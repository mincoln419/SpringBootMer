CREATE PROCEDURE `xml_temp_trunc` ()
BEGIN
	delete from house.tb_lw_xml_temp
    where inst_Dtm <= now() - 5;
END
