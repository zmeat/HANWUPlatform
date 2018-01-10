package com.utils;

import java.io.File;

public class UnifyFileName {
	  public static String getName( String filename )
	  {
	    StringBuffer sbuf = new StringBuffer( filename );
	    for( int i=0; i<sbuf.length(); i++ )
	    {
	      if( sbuf.charAt( i ) == '/' || sbuf.charAt( i ) == '\\' ) sbuf.setCharAt( i, File.separatorChar );
	    }
	    return sbuf.toString();
	  }
}
