package com.kpk.cinematickets

import com.kpk.cinematickets.theater.TheaterRepository
import com.kpk.cinematickets.theater.models.Movie
import com.kpk.cinematickets.tickets.TicketsRepository
import com.kpk.cinematickets.tickets.TicketsService
import com.kpk.cinematickets.tickets.models.InvalidScreeningException
import com.kpk.cinematickets.tickets.models.InvalidSeatsException
import com.kpk.cinematickets.wallet.InsufficientClientMoney
import com.kpk.cinematickets.wallet.WalletRepository
import com.kpk.cinematickets.wallet.WalletService
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.lang.invoke.MethodHandles
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@SpringBootTest
@Transactional
class TicketServiceIntegrationTests {

    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().canonicalName)

    @Autowired
    lateinit var theaterRepository: TheaterRepository

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var walletService: WalletService

    @Autowired
    lateinit var walletRepository: WalletRepository

    @Autowired
    lateinit var ticketsService: TicketsService

    @Autowired
    lateinit var ticketsRepository: TicketsRepository

    companion object {
        const val DEFAULT_USER_NAME = "user"
    }

    @Test
    fun test_processGroupTicketPurchase_validData() {
        usersRepository.insertDummyUser(DEFAULT_USER_NAME)
        walletService.makeDeposit(DEFAULT_USER_NAME, BigDecimal(10))
        val movieId = theaterRepository.insertMovie(Movie(null, "Movie 1", 120, "description 1", BigDecimal(5), "Actor 1.1, Actor 1.2"))
        val roomId = theaterRepository.insertRoom(roomNumber = 50)
        val seat1Id = theaterRepository.insertSeats(roomId = roomId, seatNumber = 42)
        val movieScreeningTime = LocalDateTime.now().plusHours(1).withSecond(0).withNano(0)
        val screeningId = theaterRepository.insertScreening(movieId = movieId, roomId = roomId, time = movieScreeningTime, price = BigDecimal(9.50))

        ticketsService.processGroupTicketPurchase(screeningId, listOf(seat1Id), DEFAULT_USER_NAME)

        val tickets = ticketsRepository.getTickets(DEFAULT_USER_NAME)

        assertEquals(1, tickets.size)
        assertEquals("Movie 1", tickets[0].movieTitle)
        assertEquals(50, tickets[0].screeningRoom)
        assertEquals(42, tickets[0].seatNumber)
        assertEquals(movieScreeningTime, tickets[0].screeningTime)
        assertEquals(BigDecimal("0.50"), walletRepository.getClientMoney(DEFAULT_USER_NAME))
    }

    @Test
    fun test_processGroupTicketPurchase_nonExistingScreening() {
        assertFailsWith(InvalidScreeningException::class) {
            ticketsService.processGroupTicketPurchase(1, listOf(1), DEFAULT_USER_NAME)
        }
    }

    @Test
    fun test_processGroupTicketPurchase_invalidSeatId() {
        val movieId = theaterRepository.insertMovie(Movie(null, "Movie 1", 120, "description 1", BigDecimal(5), "Actor 1.1, Actor 1.2"))
        val roomId = theaterRepository.insertRoom(roomNumber = 50)
        theaterRepository.insertSeats(roomId = roomId, seatNumber = 42)
        val screeningId = theaterRepository.insertScreening(movieId = movieId, roomId = roomId, time = LocalDateTime.now().plusHours(1), price = BigDecimal(9.50))

        assertFailsWith(InvalidSeatsException::class) {
            ticketsService.processGroupTicketPurchase(screeningId, listOf(1_000), DEFAULT_USER_NAME)
        }
    }

    @Test
    fun test_processGroupTicketPurchase_notEnoughUserBalance() {
        usersRepository.insertDummyUser(DEFAULT_USER_NAME)
        val movieId = theaterRepository.insertMovie(Movie(null, "Movie 1", 120, "description 1", BigDecimal(5), "Actor 1.1, Actor 1.2"))
        val roomId = theaterRepository.insertRoom(roomNumber = 50)
        val seat1Id = theaterRepository.insertSeats(roomId = roomId, seatNumber = 42)
        val screeningId = theaterRepository.insertScreening(movieId = movieId, roomId = roomId, time = LocalDateTime.now().plusHours(1), price = BigDecimal(9.50))

        assertFailsWith(InsufficientClientMoney::class) {
            ticketsService.processGroupTicketPurchase(screeningId, listOf(seat1Id), DEFAULT_USER_NAME)
        }
    }


}
