-- mvn org.flywaydb:flyway-maven-plugin:11.15.0:repair -Dflyway.url=jdbc:postgresql://localhost:5437/trip -Dflyway.user=user -Dflyway.password=password

DROP TABLE IF EXISTS route;
DROP TABLE IF EXISTS stop;
DROP TABLE IF EXISTS route_stop;
DROP TABLE IF EXISTS bus;

-- Route table
CREATE TABLE route (
       id UUID PRIMARY KEY,
       route_number VARCHAR(255) NOT NULL,
       name VARCHAR(100),
       description VARCHAR(255),
       active BOOLEAN NOT NULL,
       price DOUBLE PRECISION NOT NULL
);

-- Stop table
CREATE TABLE stop (
      id UUID PRIMARY KEY,
      name VARCHAR(100) NOT NULL,
      latitude DOUBLE PRECISION NOT NULL,
      longitude DOUBLE PRECISION NOT NULL,
      address VARCHAR(255) NOT NULL
);

-- RouteStop table
CREATE TABLE route_stop (
        id UUID PRIMARY KEY,
        stop_order INT NOT NULL,
        distance_from_previous INT,
        estimated_travel_time INT,
        far_from_start NUMERIC(10,2),
        route_id UUID NOT NULL,
        stop_id UUID NOT NULL,
        CONSTRAINT fk_route FOREIGN KEY (route_id) REFERENCES route(id),
        CONSTRAINT fk_stop FOREIGN KEY (stop_id) REFERENCES stop(id)
);

-- Bus table
CREATE TABLE bus (
     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
     registration_number VARCHAR(255) NOT NULL,
     capacity INTEGER,
     start_time TIME,
     end_time TIME,
     bus_status VARCHAR(50),
     last_maintenance DATE,
     CONSTRAINT fk_route FOREIGN KEY (route_id) REFERENCES route(id),
);
