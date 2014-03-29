<?php

	$link = mysql_connect('mysql12.000webhost.com', 'a5150163_planind', 'planindia123');

	if (!$link)
		{
			die('Could not connect: ' . mysql_error());
		}
	mysql_select_db('a5150163_planind');

	$result1 = mysql_query("SELECT * FROM GROUPS");
 
    if (!empty($result1)) 
    {
        // check for empty result
        if (mysql_num_rows($result1) > 0) 
        {                         
        		   Print "<table border cellpadding=3>"; 
			   print "<tr>";            
                           print "<th> UId </th>";
			   print "<th> Group Name </th>";
			   print "<th> Group Address </th>";
			   print "<th> Fo Id </th>";
			   print "<th> President Id </th>";
			   print "<th> Recurring Saving </th>";
			   print "<th> Created Date </th>";
			   print "<th> Created By </th>";
			   print "<th> Last Modified Date </th>";
			   print "<th> Last Modified Id </th></tr>";
			   while($result = mysql_fetch_array($result1))
                           {			            			
		              	print"<tr>
					<td>".$result["UID"]."</td>
					<td>".$result["GROUP_NAME"]."</td>
					<td>".$result["GROUP_ADDRESS"]."</td>
					<td>".$result["FO_ID"]."</td>
					<td>".$result["PRES_ID"]."</td>
					<td>".$result["RECURRING_SAVING"]."</td>
					<td>".$result["CREATED_DATE"]."</td>
					<td>".$result["CREATED_BY"]."</td>
					<td>".$result["LAST_MODIFIED_DATE"]."</td>
					<td>".$result["LAST_MODIF_ID"]."</td>
					</tr>";				
                           }
            // echoing JSON response
            print "</table>";
	}
        else
        {
           print "No Data present in Groups Table";
        }
   }     
 ?>
