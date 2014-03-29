<?php

$link = mysql_connect('mysql12.000webhost.com', 'a5150163_planind', 'planindia123');

if (!$link)
{
	die('Could not connect: ' . mysql_error());
}
else
{
mysql_select_db('a5150163_planind');

$json = file_get_contents('php://input');
$groups_array = json_decode($json,true);

$response = array("success" => 0, "error" => 0);  
foreach($groups_array as $group)
{
//     $query = "INSERT INTO GROUPS (UID, GROUP_NAME, GROUP_ADDRESS, FO_ID, PRES_ID, RECURRING_SAVING, CREATED_DATE, CREATED_BY, LAST_MODIFIED_DATE, LAST_MODIF_ID) VALUES ('$group[UID]','$group[GroupName]','$group[GroupAddress]','$group[FOId]','$group[PresId]','$group[RecurringSaving]','$group[CreatedAt]','$group[CreatedBy]')";

       $selectRes = mysql_query("SELECT UID FROM GROUPS WHERE UID='$group[UID]'");
       if($selectRes)
       {
           $deleteRes = mysql_query("DELETE FROM GROUPS WHERE UID='$group[UID]'");
           echo "Record Deleted";
       }
       else 
       {
           echo "Nothing";
       }
       echo $group["GroupName"];
       $query = "INSERT INTO GROUPS (UID, GROUP_NAME, GROUP_ADDRESS, RECURRING_SAVING) VALUES ('$group[UID]','$group[GroupName]','$group[GroupAddress]','$group[RecurringSaving]')";
       $result = mysql_query($query) or die(mysql_error());
       if(!$result)
       {
          $response["error"] = 1;
          echo "Record Not Inserted";
       }
       else
       {
          $response["success"] = 1;
          echo "Record Inserted";
       }
     
}
mysql_close($link);
}
?>
