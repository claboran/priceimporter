-- drop if needed
drop SEQUENCE if EXISTS region_seq;
drop SEQUENCE if EXISTS period_type_seq;
drop SEQUENCE if EXISTS date_time_seq;
drop SEQUENCE if EXISTS fact_seq;

drop TABLE IF EXISTS f_energy_price_demand;
drop TABLE IF EXISTS d_region;
drop TABLE IF EXISTS d_period_type;
drop TABLE IF EXISTS d_date_time;

