let min_price = 0;
let max_price = 100;

$("#min-price").on("change mousemove", function () {
  min_price = parseInt($("#min-price").val());
  $("#min-price-txt").text(min_price + "€");
});

$("#max-price").on("change mousemove", function () {
  max_price = parseInt($("#max-price").val());
  $("#max-price-txt").text(max_price + "€");
});


  //set codes
  //this section is for percentage off codes
  const free = ['free'];
  //this section is for dollar-value codes
  const dollars10 = ['diez'];
  const dollars15 = ['quince'];
  const shipping =['freeshipping'];
  //set default values to no discount
  let percentDiscount = 1;
  let priceDiscount = 0;



$("#coupon").change( function() {
  let price = parseInt($("#price").text())
  let coupon = String($("#coupon").val())
  let found=false;

  if(free.includes(coupon)){
    percentDiscount=0
    $("#shipping").text("5€")
    found=true
  }

  //adjust dollar-value variable if the coupon code entered matches
  if(dollars10.includes(coupon)){
    priceDiscount=10
    $("#shipping").text("5€")
    found=true
  }
  else{
    if(dollars15.includes(coupon)){
      priceDiscount=15
      $("#shipping").text("5€")
      found=true
    }
  }

  if(shipping.includes(coupon)){
    priceDiscount=5
    found=true
    $("#shipping").text("Gastos de envío gratis")
  }

  let total = Math.round(price*percentDiscount-priceDiscount)

  if(total < 0){total = 0}

  if(!found){
    $("#err").text("Cupón inválido")
    $("#message").text("")
    $("#shipping").text("5€")
    $("#price").show()
    $("#reprice").text("")
    $("#total").text("Total")
    $("#finalPrice").val(price)
  }else{
    $("#err").text("")
    $("#message").text("Cupón aplicado!")
    $("#total").text("Total con descuento")
    $("#reprice").text(total+"€")
    $("#price").hide()
    $("#finalPrice").val(total)
  }

});

function passwordsMatch(id1,id2) {
  var password1 = document.getElementById(id1).value;
  var password2 = document.getElementById(id2).value;
  if (password1!==password2) {
    $('#dialog').alert();
    return false;
  }
  return true;
}





