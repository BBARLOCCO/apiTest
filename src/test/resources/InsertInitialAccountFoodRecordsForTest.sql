delete from account_food_entry;
delete from account;

INSERT INTO account values('cf2e8ffc-0aa6-4bc4-b414-2b99038bc8cf','user@test.com', 'User 2', 'Test', 'xxx',null,2100 ,'cf2e8ffc-0aa6-4bc4-b414-2b99038bc8ad', current_timestamp, current_timestamp);
INSERT INTO account values('cf2e8ffc-0aa6-4bc4-b414-2b99038bc8df','user@test.com', 'User 2', 'Test', 'xxx',null,2100 ,'cf2e8ffc-0aa6-4bc4-b414-2b99038bc8ad', current_timestamp, current_timestamp);

INSERT INTO account_food_entry (id,account_id,name,calories,entry_date,created_at,updated_at) VALUES
	 ('3a94fdb0-25c2-45aa-9f4f-aee80875d6c8'::uuid,'cf2e8ffc-0aa6-4bc4-b414-2b99038bc8cf'::uuid,'kiwi juice',100,'2022-01-01 12:00:00.000','2022-01-10 13:01:32.338','2022-01-10 13:01:32.338'),
	 ('48ec68aa-83a7-4bbd-acc8-e8669d362e21'::uuid,'cf2e8ffc-0aa6-4bc4-b414-2b99038bc8cf'::uuid,'jicama',100,'2022-01-01 13:35:34.000','2022-01-10 13:02:53.009','2022-01-10 13:02:53.009'),
	 ('59a21998-6bf5-4bfd-a20a-43626a270aa8'::uuid,'cf2e8ffc-0aa6-4bc4-b414-2b99038bc8cf'::uuid,'olive oil',100,'2022-01-01 14:14:53.000','2022-01-10 13:04:09.225','2022-01-10 13:04:09.225'),
	 ('0f9cf56b-4228-4dd0-a542-be20293d3b8e'::uuid,'cf2e8ffc-0aa6-4bc4-b414-2b99038bc8cf'::uuid,'beer',100,'2022-01-01 16:20:46.000','2022-01-10 15:18:12.087','2022-01-10 15:18:12.087'),
	 ('494c4e3d-bef5-43eb-a648-27722d4e9160'::uuid,'cf2e8ffc-0aa6-4bc4-b414-2b99038bc8cf'::uuid,'coca cola',100,'2022-01-01 15:59:19.000','2022-01-10 15:28:33.698','2022-01-10 15:28:33.698'),
	 ('3a94fdb0-25c2-45aa-9f4f-aee80875d6ca'::uuid,'cf2e8ffc-0aa6-4bc4-b414-2b99038bc8df'::uuid,'kiwi juice',100,'2022-01-02 12:00:00.000','2022-01-10 13:01:32.338','2022-01-10 13:01:32.338'),
	 ('48ec68aa-83a7-4bbd-acc8-e8669d362e2a'::uuid,'cf2e8ffc-0aa6-4bc4-b414-2b99038bc8df'::uuid,'jicama',100,'2022-01-02 13:35:34.000','2022-01-10 13:02:53.009','2022-01-10 13:02:53.009'),
	 ('59a21998-6bf5-4bfd-a20a-43626a270aab'::uuid,'cf2e8ffc-0aa6-4bc4-b414-2b99038bc8df'::uuid,'olive oil',100,'2022-01-02 14:14:53.000','2022-01-10 13:04:09.225','2022-01-10 13:04:09.225'),
	 ('0f9cf56b-4228-4dd0-a542-be20293d3b8c'::uuid,'cf2e8ffc-0aa6-4bc4-b414-2b99038bc8df'::uuid,'beer',100,'2022-01-02 16:20:46.000','2022-01-10 15:18:12.087','2022-01-10 15:18:12.087'),
	 ('494c4e3d-bef5-43eb-a648-27722d4e916d'::uuid,'cf2e8ffc-0aa6-4bc4-b414-2b99038bc8df'::uuid,'coca cola',150,'2022-01-02 15:59:19.000','2022-01-10 15:28:33.698','2022-01-10 15:28:33.698');