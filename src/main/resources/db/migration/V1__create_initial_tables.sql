-- USERS
CREATE TABLE users (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL
);

-- SERVICES
CREATE TABLE services (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(255) NOT NULL,
    duration_minutes INT NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    active BOOLEAN NOT NULL
);

-- AVAILABILITIES
CREATE TABLE availabilities (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    staff_id UUID NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);

-- RESERVATIONS
CREATE TABLE reservations (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    client_id UUID NOT NULL,
    staff_id UUID NOT NULL,
    service_id UUID NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL
);