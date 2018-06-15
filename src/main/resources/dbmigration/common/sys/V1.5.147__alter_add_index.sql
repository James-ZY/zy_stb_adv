alter table ad_channel  add index ad_network_id (ad_network_id); 
alter table ad_type_channel  add index ad_channel_id (ad_channel_id); 
alter table ad_type_network  add index ad_network_id (ad_network_id); 