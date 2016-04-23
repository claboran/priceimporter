-- Region dimension

create SEQUENCE int_test_region_seq START WITH 1 INCREMENT BY 1;

create TABLE int_test_d_region (
  id BIGINT PRIMARY KEY NOT NULL,
  region NVARCHAR(50)
);

-- PeriodType dimension

create SEQUENCE int_test_period_type_seq START WITH 1 INCREMENT BY 1;

create TABLE int_test_d_period_type (
  id BIGINT PRIMARY KEY NOT NULL,
  period_type NVARCHAR(50)
);

-- Time date dimension

create SEQUENCE int_test_date_time_seq START WITH 1 INCREMENT BY 1;

create TABLE int_test_d_date_time (
  id BIGINT PRIMARY KEY NOT NULL,
  day_of_month INTEGER,
  month_of_year INTEGER,
  the_year INTEGER,
  hour_of_day INTEGER,
  minutes_of_hour INTEGER
);

-- Fact table

create SEQUENCE int_test_fact_seq START WITH 1 INCREMENT BY 1;

create TABLE int_test_f_energy_price_demand (
  id BIGINT PRIMARY KEY NOT NULL,
  total_Demand DECIMAL(20,2),
  rpr DECIMAL(20,2),
  d_region_id BIGINT NOT NULL,
  d_period_type_id BIGINT NOT NULL,
  d_date_time_id BIGINT NOT NULL,
  FOREIGN KEY (d_region_id) REFERENCES int_test_d_region(id),
  FOREIGN KEY (d_period_type_id) REFERENCES int_test_d_period_type(id),
  FOREIGN KEY (d_date_time_id) REFERENCES int_test_d_date_time(id)
);