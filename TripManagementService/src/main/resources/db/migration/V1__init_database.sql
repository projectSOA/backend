DROP TABLE IF EXISTS route;
DROP TABLE IF EXISTS stop;

-- Route table
CREATE TABLE route (
       id UUID PRIMARY KEY,
       route_number VARCHAR(255) NOT NULL,
       name VARCHAR(100),
       description VARCHAR(255),
       active BOOLEAN NOT NULL
);

-- Stop table
CREATE TABLE stop (
      id UUID PRIMARY KEY,
      name VARCHAR(100) NOT NULL,
      latitude DOUBLE PRECISION NOT NULL,
      longitude DOUBLE PRECISION NOT NULL,
      address VARCHAR(255) NOT NULL
);