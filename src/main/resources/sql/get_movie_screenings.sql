SELECT s.id screening_id,
       s.time screening_time,
       r.number room_number
from screenings s
join movies m on s.movie_id = m.id
join rooms r on s.room_id = r.id
where m.id = :movieId
