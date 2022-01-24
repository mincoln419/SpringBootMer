/**
 * @packageName : com.mermer.mermerbatch.core.util
 * @fileName : ClobConverter.java 
 * @author : Mermer 
 * @date : 2022.01.25 
 * @description :
 * =========================================================== 
 * DATE AUTHOR NOTE 
 * ----------------------------------------------------------- 
 * 2022.01.25 Mermer 최초 생성
 */
package com.mermer.mermerbatch.core.util;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.util.stream.Collectors;

public class ClobConverter {

	public static String clobToString( Clob clob ) throws Exception {

	    String result = null;
	    Reader reader = null;
	    BufferedReader br = null;
	    try {
	        reader = clob.getCharacterStream();
	        br = new BufferedReader( reader );
	        result = br.lines().collect( Collectors.joining() );
	    }
	    catch( Exception e ) {
	        e.printStackTrace();
	    }
	    finally {
	        if( reader != null ) {
	            reader.close();
	        }
	        if( br != null ) {
	             br.close();
	        }
	    }

	    return result;
	}
	
}
