ALTER TABLE ad_element DROP COLUMN ad_id; 
ALTER TABLE ad_element  ADD  ad_id varchar(255);
ALTER TABLE ad_combo DROP COLUMN ad_combo_id; 
ALTER TABLE ad_combo  ADD  ad_combo_id varchar(255);