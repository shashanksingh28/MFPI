<?php

	require_once 'DbConnect.php';

	//Connect to Database
	$db = new DB_CONNECT();

// Sync-In feature begins

	//Get Input from Android in form of Json and 
	$json = file_get_contents('php://input');
	$GroupsArray = json_decode($json,true);
	
	foreach($GroupsArray as $Group)
	{
		$FOId = $Group[FieldOfficerId];
		$SelectRowsForDelete = mysql_query("SELECT Id FROM Groups WHERE Id = '$Group[Id]' and ModifiedDate < '$Group[ModifiedDate]'");
		if($SelectRowsForDelete)
		{
			$DeleteRows = mysql_query("DELETE FROM Groups where Id = '$Group[Id]'");
		}                

        	$InsertData = "INSERT INTO Groups (
												Id,
                                                Name,
                                                PresidentId, 
                                                SecretaryId,
                                                TreasurerId,
                                                FieldOfficerId,
                                                Active,
                                                MonthlyCompulsoryAmount,
                                                MonthlyMeetingDate,
                                                Bank,
                                                ClusterId,
                                                CummulativeSavings,
                                                OtherIncome,
                                                OutstandingLoans,
                                                DateOfFormation,
                                                NoOfSubgroups,
                                                AddressLine1,
                                                AddressLine2,
                                                City,
                                                State,
                                                Country,
                                                CreatedDate,
                                                CreatedBy,
                                                ModifiedDate,
                                                ModifiedBy,
                                                NoOfActiveMembers
                                            ) 
                                    VALUES (
											'$Group[Id]',
                                                '$Group[Name]',
                                                '$Group[PresidentId]', 
                                                '$Group[SecretaryId]', 
                                                '$Group[TreasurerId]',
                                                '$Group[FieldOfficerId]',
                                                '$Group[Active]',
                                                '$Group[MonthlyCompulsoryAmount]',
                                                '$Group[MonthlyMeetingDate]',
                                                '$Group[Bank]',
                                                '$Group[ClusterId]',
                                                '$Group[CummulativeSavings]',
                                                '$Group[OtherIncome]',
                                                '$Group[OutstandingLoans]',
                                                '$Group[DateOfFormation]',
                                                '$Group[NoOfSubgroups]',
                                                '$Group[AddressLine1]',
                                                '$Group[AddressLine2]',
                                                '$Group[City]',
                                                '$Group[State]',
                                                '$Group[Country]',
                                                '$Group[CreatedDate]',
                                                '$Group[CreatedBy]',
                                                '$Group[ModifiedDate]',
                                                '$Group[ModifiedBy]',
                                                '$Group[NoOfActiveMembers]'
                                      )";
		
		$ExecuteQuery = mysql_query($InsertData) or die(mysql_error());
		if($ExecuteQuery)
		{
			//echo "Group Data Inserted";
		}
		else
		{
			echo "Inserting Group Data Failed";
		}
	}
		
// Sync-Out Feature begins
		$SelectAllData = mysql_query("SELECT * FROM Groups where FieldOfficerId = '$FOId'");
		$ReturnArray = array();
		$count = 0;
        while($SelectRowData = mysql_fetch_array($SelectAllData))
	{
//			array_push($ReturnArray, $SelectRowData);	

												$ReturnArray[$count][Id] =												$SelectRowData[Id];
                                                $ReturnArray[$count][Name] =                                                $SelectRowData[Name];
                                                $ReturnArray[$count][PresidentId] =                                                $SelectRowData[PresidentId];
                                                $ReturnArray[$count][SecretaryId] =                                                $SelectRowData[SecretaryId];
                                                $ReturnArray[$count][TreasurerId] =                                                $SelectRowData[TreasurerId];
                                                $ReturnArray[$count][FieldOfficerId] =                                                $SelectRowData[FieldOfficerId];
                                                $ReturnArray[$count][Active] =                                                $SelectRowData[Active];
                                                $ReturnArray[$count][MonthlyCompulsoryAmount] =                                                $SelectRowData[MonthlyCompulsoryAmount];
                                                $ReturnArray[$count][MonthlyMeetingDate] =                                                $SelectRowData[MonthlyMeetingDate];
                                                $ReturnArray[$count][Bank] =                                                $SelectRowData[Bank];
                                                $ReturnArray[$count][ClusterId] =                                                $SelectRowData[ClusterId];
                                                $ReturnArray[$count][CummulativeSavings] =                                                $SelectRowData[CummulativeSavings];
                                                $ReturnArray[$count][OtherIncome] =                                                $SelectRowData[OtherIncome];
                                                $ReturnArray[$count][OutstandingLoans] =                                                $SelectRowData[OutstandingLoans];
                                                $ReturnArray[$count][DateOfFormation] =                                                $SelectRowData[DateOfFormation];
                                                $ReturnArray[$count][NoOfSubgroups] =                                                $SelectRowData[NoOfSubgroups];
                                                $ReturnArray[$count][AddressLine1] =                                                $SelectRowData[AddressLine1];
                                                $ReturnArray[$count][AddressLine2] =                                                $SelectRowData[AddressLine2];
                                                $ReturnArray[$count][City] =                                                $SelectRowData[City];
                                                $ReturnArray[$count][State] =                                                $SelectRowData[State];
                                                $ReturnArray[$count][Country] =                                                $SelectRowData[Country];
                                                $ReturnArray[$count][CreatedDate] =                                                $SelectRowData[CreatedDate];
                                                $ReturnArray[$count][CreatedBy] =                                                $SelectRowData[CreatedBy];
                                                $ReturnArray[$count][ModifiedDate] =                                                $SelectRowData[ModifiedDate];
                                                $ReturnArray[$count][ModifiedBy] =                                                $SelectRowData[ModifiedBy];
                                                $ReturnArray[$count][NoOfActiveMembers] = $SelectRowData[NoOfActiveMembers];
								$count = $count + 1;
	}
	echo json_encode($ReturnArray);	

?>
