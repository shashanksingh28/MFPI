<?php

    require_once 'DbConnect.php';

	//Connect to Database
    $db = new DB_CONNECT();    

// Sync-In feature begins

	//Get Input from Android in form of Json 
	$json = file_get_contents('php://input');
	$SavingAccountsArray = json_decode($json,true);
	
	foreach($SavingAccountsArray as $SavingAccount)
	{
	    $GrpId = $SavingAccount[GroupId];            
		$SelectRowsForDelete = mysql_query("SELECT Id FROM SavingAccounts WHERE Id = '$SavingAccount[Id]' AND GroupId = '$SavingAccount[GroupId]'");
		if($SelectRowsForDelete)
		{
			$DeleteRows = mysql_query("DELETE FROM SavingAccounts WHERE Id = '$SavingAccount[Id]' AND GroupId = '$SavingAccount[GroupId]'");
		}
		$InsertData = "INSERT INTO SavingAccounts (
														Id,
														MemberId,
														GroupId,
														CompulsorySavings,
														OptionalSavings,
														InterestAccumulated,
														TotalSavings,
														CreatedDate,
														CreatedBy,
														Active
														)
													VALUES (
													    '$SavingAccount[Id]',
														'$SavingAccount[MemberId]',
														'$SavingAccount[GroupId]',
														'$SavingAccount[CompulsorySavings]',
														'$SavingAccount[OptionalSavings]',
														'$SavingAccount[InterestAccumulated]',
														'$SavingAccount[TotalSavings]',
														'$SavingAccount[CreatedDate]',
														'$SavingAccount[CreatedBy]',
														'$SavingAccount[Active]'																												
													)";
															
		$ExecuteQuery = mysql_query($InsertData) or die(mysql_error());
		if($ExecuteQuery)
		{
			//echo "Saving Account Data Inserted";
		}
		else
		{
			echo "Saving Account Data Failed";
		}
	}

// Sync-Out Feature begins
		$SelectAllData = mysql_query("SELECT * FROM SavingAccounts WHERE GroupId IN (SELECT Id FROM Groups WHERE FieldOfficerId IN ( SELECT FieldOfficerId FROM Groups WHERE Id = '$GrpId'))");
		$ReturnArray = array();
	
        while($SelectRowData = mysql_fetch_array($SelectAllData))
	{
			array_push($ReturnArray, $SelectRowData);	
	}
	echo json_encode($ReturnArray);		
	
?>
