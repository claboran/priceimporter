-- drop if needed
drop SEQUENCE if EXISTS int_test_region_seq;
drop SEQUENCE if EXISTS int_test_period_type_seq;
drop SEQUENCE if EXISTS int_test_date_time_seq;
drop SEQUENCE if EXISTS int_test_fact_seq;

drop TABLE IF EXISTS int_test_f_energy_price_demand;
drop TABLE IF EXISTS int_test_d_region;
drop TABLE IF EXISTS int_test_d_period_type;
drop TABLE IF EXISTS int_test_d_date_time;

