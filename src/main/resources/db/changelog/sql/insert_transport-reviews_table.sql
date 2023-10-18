INSERT INTO transport_reviews (rating, comment, created, user_id, transport_id)
VALUES
    (5, 'Goog!', now(), 1, 1),
    (4, 'Normal', now(), 1, 2),
    (4, 'Not bad!', now(), 1, 3),
    (3, 'I do not recommend!', now(), 1, 4),
    (2, 'Bad!', now(), 1, 5),
    (5, 'Very nice!', now(), 1, 6),

    (5, 'Goog!', now(), 2, 7),
    (4, 'Normal', now(), 2, 8),
    (4, 'Not bad!', now(), 2, 2),
    (3, 'I do not recommend!', now(), 2, 1),
    (2, 'Bad!', now(), 2, 4),
    (5, 'Very nice!', now(), 2, 12);
