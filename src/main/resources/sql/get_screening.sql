SELECT m.id movie_id,
       m.title,
       m.description,
       m.length_minutes,
       m.rating,
       m.actors,
       s.id screening_id,
       s.time screening_time,
       s.price
from screenings s
     join movies m on s.movie_id = m.id
where s.id = :screeningId
