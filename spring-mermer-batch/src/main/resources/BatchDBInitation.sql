delete from house.BATCH_JOB_EXECUTION_CONTEXT
where JOB_EXECUTION_ID >=1;
delete from house.BATCH_JOB_EXECUTION_PARAMS
where JOB_EXECUTION_ID >=1;
delete from house.BATCH_JOB_EXECUTION_SEQ 
where id >= 1;
delete from house.BATCH_JOB_SEQ
where ID >=1;
delete from house.BATCH_STEP_EXECUTION_CONTEXT
where STEP_EXECUTION_ID >=1;
delete from house.BATCH_STEP_EXECUTION_SEQ
where STEP_EXECUTION_ID >=1;
delete from house.BATCH_STEP_EXECUTION
where STEP_EXECUTION_ID >=1;
delete from house.BATCH_JOB_EXECUTION
where job_EXECUTION_ID >=1;
delete from house.BATCH_JOB_INSTANCE
where JOB_INSTANCE_ID >=1;

INSERT INTO house.BATCH_STEP_EXECUTION_SEQ values(0, '0');
INSERT INTO house.BATCH_JOB_EXECUTION_SEQ values(0, '0');
INSERT INTO house.BATCH_JOB_SEQ values(0, '0');

commit;
