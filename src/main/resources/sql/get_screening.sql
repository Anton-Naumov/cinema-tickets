SELECT m.id movie_id,
       m.title,
       m.description,
       m.length_minutes,
       m.rating,
       m.actors,
       s.id screening_id,
       s.time screening_time,
       s.price,
       r.number room_number
from screenings s
    join movies m on s.movie_id = m.id
    join rooms r on s.room_id = r.id
where s.id = :screeningId
