<?php

    require_once 'DbConnect.php';

	//Connect to Database
    $db = new DB_CONNECT();

// Sync-In feature begins

	//Get Input from Android in form of Json 
	$json = file_get_contents('php://input');
	$SavingTransactionsArray = json_decode($json,true);
	$SyncOutArray = array();
	$count = 0;
	
	foreach($SavingTransactionsArray as $SavingTransaction)
	{
	// Re-Iterate
		
		$GrpId = $SavingTransaction[GroupId];
		$DeleteRows = mysql_query("DELETE FROM SavingAccountTransactions WHERE  GroupId = '$SavingTransaction[GroupId]' AND 
																				 SavingAccountId = '$SavingTransaction[SavingAccountId]' AND
																				 MeetingId = '$SavingTransaction[MeetingId]' AND
																				 Type = '$SavingTransaction[Type]'
																				 ");
		
		$InsertData = "INSERT INTO SavingAccountTransactions (
															GroupId,
															SavingAccountId,
															MeetingId,
															Type,
															Amount,
															CurrentBalance,
															DateTime
															)
													VALUES (
															'$SavingTransaction[GroupId]',
															'$SavingTransaction[SavingAccountId]',
															'$SavingTransaction[MeetingId]',
															'$SavingTransaction[Type]',
															'$SavingTransaction[Amount]',
															'$SavingTransaction[CurrentBalance]',
															'$SavingTransaction[DateTime]'  
															)";

		$ExecuteQuery = mysql_query($InsertData) or die(mysql_error());
		if($ExecuteQuery)
		{
			//echo "SavingAccountTransactions Data Inserted";
		}
		else
		{
			echo "Inserting SavingAccountTransactions Data Failed";
		}
	}



// Sync-Out Feature begins

	$ReturnArray = array();		
	$SelectAllData = mysql_query("SELECT * FROM SavingAccountTransactions WHERE GroupId IN (SELECT Id FROM Groups WHERE FieldOfficerId IN ( SELECT FieldOfficerId FROM Groups WHERE Id = '$GrpId'))");
	
    while($SelectRowData = mysql_fetch_array($SelectAllData))
	{
			array_push($ReturnArray, $SelectRowData);	
	}

	echo json_encode($ReturnArray);	
	
?>
