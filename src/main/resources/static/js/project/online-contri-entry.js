$("#delayedMonthAndYearCombos").hide()
$("#DAArrearsDatesDivDisplay").hide()
$("#PayArrearsDatesDivDisplay").hide()
function addMonthYearComboForDelayed() {
	var typeOfPaymentMaster = $("#cmbTypeOfPaymentMaster").val();
	if (typeOfPaymentMaster == '700047') {
		$("#delayedMonthAndYearCombos").fadeIn(100);
	} else {
		$("#delayedMonthAndYearCombos").hide()
	}

	if (typeOfPaymentMaster == '700048') {
		$("#DAArrearsDatesDivDisplay").fadeIn(100);
	} else {
		$("#DAArrearsDatesDivDisplay").hide();
	}

	if (typeOfPaymentMaster == '700049') {
		$("#PayArrearsDatesDivDisplay").fadeIn(100);
	} else {
		$("#PayArrearsDatesDivDisplay").hide();
	}
}