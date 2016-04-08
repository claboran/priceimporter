-- drop if needed
drop SEQUENCE if EXISTS region_seq;
drop SEQUENCE if EXISTS period_type_seq;
drop SEQUENCE if EXISTS date_time_seq;
drop SEQUENCE if EXISTS fact_seq;

drop TABLE IF EXISTS f_energy_price_demand;
drop TABLE IF EXISTS d_region;
drop TABLE IF EXISTS d_period_type;
drop TABLE IF EXISTS d_date_time;

-- Region dimension

create SEQUENCE region_seq START WITH 1 INCREMENT BY 1;

create TABLE d_region (
  id BIGINT PRIMARY KEY NOT NULL,
  region NVARCHAR(50)
);

-- PeriodType dimension

create SEQUENCE period_type_seq START WITH 1 INCREMENT BY 1;

create TABLE d_period_type (
  id BIGINT PRIMARY KEY NOT NULL,
  period_type NVARCHAR(50)
);

-- Time date dimension

create SEQUENCE date_time_seq START WITH 1 INCREMENT BY 1;

create TABLE d_date_time (
  id BIGINT PRIMARY KEY NOT NULL,
  day_of_month INTEGER,
  month_of_year INTEGER,
  _year INTEGER,
  hour_of_day INTEGER,
  minutes_of_hour INTEGER
);

-- Fact table

create SEQUENCE fact_seq START WITH 1 INCREMENT BY 1;

create TABLE f_energy_price_demand (
  id BIGINT PRIMARY KEY NOT NULL,
  total_Demand DECIMAL(20,2),
  rpr DECIMAL(20,2),
  d_region_id BIGINT NOT NULL,
  d_period_type_id BIGINT NOT NULL,
  d_date_time_id BIGINT NOT NULL,
  FOREIGN KEY (d_region_id) REFERENCES d_region(id),
  FOREIGN KEY (d_period_type_id) REFERENCES d_period_type(id),
  FOREIGN KEY (d_date_time_id) REFERENCES d_date_time(id)
);