package com.kpk.cinematickets.notifications

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.lang.invoke.MethodHandles


interface NotificationService {
    fun sendNotification(receiver: String, subject: String, content: String)
}

@Service
class NotificationServiceImpl(
        val javaMailSender: JavaMailSender,
        @Value("\${spring.mail.username}") val emailSender: String
): NotificationService {

    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().canonicalName)

    override fun sendNotification(receiver: String, subject: String, content: String) {
        val message = SimpleMailMessage()
        message.setFrom(emailSender)
        message.setTo(receiver)
        message.setSubject(subject)
        message.setText(content)
        javaMailSender.send(message)
        logger.info("Notification send to user {}", receiver)
    }

}
