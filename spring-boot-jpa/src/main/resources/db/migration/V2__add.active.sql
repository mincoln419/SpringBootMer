ALTER TABLE account ADD COLUMN active BOOLEAN;

-- 데이터 변경도 가능하고... 기존의 DDL 이력을 남길 수 있다는 점에서 매우 유용함
