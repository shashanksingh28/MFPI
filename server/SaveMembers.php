<?php

	require_once 'DbConnect.php';

	//Connect to Database
    $db = new DB_CONNECT();
   
// Sync-In feature begins
   
	//Get Input from Android in form of Json and 
	$json = file_get_contents('php://input');
    $MembersArray = json_decode($json,true);
        
    foreach($MembersArray as $Member)
	{
		$GrpId = $Member[GroupId];
		$SelectRowsForDelete = mysql_query("SELECT Id FROM Members WHERE Id = '$Member[Id]' AND GroupId = '$Member[GroupId]' ");
		if($SelectRowsForDelete)
		{
			$DeleteRows = mysql_query("DELETE FROM Members WHERE Id = '$Member[Id]' AND GroupId = '$Member[GroupId]'");
		}
                
		$InsertData = "INSERT INTO Members ( 
											Id,
											GroupId,
											FirstName,
											LastName,
											GuardianName,
											Gender,
											DOB,
											EmailId,
											Active,
											ContactNumber,
											AddressLine1,
											AddressLine2,
											Occupation,
											AnnualIncome,
											Education,
											Disability,
											NoOfFamilyMembers,
											Nominee,
											Insurance,
											ExitDate,
											ExitReason,
											CreatedDate,
											CreatedBy,
											ModifiedDate,
											ModifiedBy,
											Passbook,
											EconomicCondition,
											CurrentSavings,
											CurrentOutstanding
                                           ) 
                                       VALUES (
											'$Member[Id]',
											'$Member[GroupId]',
											'$Member[FirstName]',
											'$Member[LastName]',
											'$Member[GuardianName]',
											'$Member[Gender]',
											'$Member[DOB]',
											'$Member[EmailId]',
											'$Member[Active]',
											'$Member[ContactNumber]',
											'$Member[AddressLine1]',
											'$Member[AddressLine2]',											
											'$Member[Occupation]',
											'$Member[AnnualIncome]',
											'$Member[Education]',
											'$Member[Disability]',
											'$Member[NoOfFamilyMembers]',
											'$Member[Nominee]',
											'$Member[Insurance]',
											'$Member[ExitDate]',
											'$Member[ExitReason]',
											'$Member[CreatedDate]',
											'$Member[CreatedBy]',
											'$Member[ModifiedDate]',
											'$Member[ModifiedBy]',
											'$Member[Passbook]',
											'$Member[EconomicCondition]',
											'$Member[CurrentSavings]',
											'$Member[CurrentOutstanding]'                                      
											)";
		
		$ExecuteQuery = mysql_query($InsertData) or die(mysql_error());
		if($ExecuteQuery)
		{
			//echo "Members Data Inserted";
		}
		else
		{
			echo "Inserting Members Data Failed";
		}
	}
	
// Sync-Out Feature begins
		$SelectAllData = mysql_query("SELECT * FROM Members where GroupId IN (SELECT Id FROM Groups WHERE FieldOfficerId IN ( SELECT FieldOfficerId FROM Groups WHERE Id = '$GrpId'))");
		$ReturnArray = array();
	
        while($SelectRowData = mysql_fetch_array($SelectAllData))
	{
			array_push($ReturnArray, $SelectRowData);	
	}
	echo json_encode($ReturnArray);	
	
	
?>	
