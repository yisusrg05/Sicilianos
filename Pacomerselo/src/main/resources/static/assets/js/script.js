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
  const percent10 = ['ten'];
  const percent20 = ['twenty'];
  //this section is for dollar-value codes
  const dollars20 = ['dollarcode'];
  const dollars25 = ['dollarcode2'];
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
  else{
    if(percent10.includes(coupon)){
      percentDiscount=.9
      $("#shipping").text("5€")
      found=true
    }
  else{
    if(percent20.includes(coupon)){
      percentDiscount=.8
      $("#shipping").text("5€")
      found=true
    }
  }
  }

  //adjust dollar-value variable if the coupon code entered matches
  if(dollars20.includes(coupon)){
    priceDiscount=20
    $("#shipping").text("5€")
    found=true
  }
  else{
    if(dollars25.includes(coupon)){
      priceDiscount=25
      $("#shipping").text("5€")
      found=true
    }
  }

  if(shipping.includes(coupon)){
    priceDiscount=5
    found=true
    $("#shipping").text("Gastos de envío gratis")
  }

  let total = price*percentDiscount-priceDiscount
  if(total < 0){total = 0}
  if(!found){
    $("#err").text("Cupón inválido")
    $("#message").text("")
    $("#shipping").text("5€")
    $("#price").show()
    $("#reprice").text("")
    $("#total").text("Total")
  }else{
    $("#err").text("")
    $("#message").text("Cupón aplicado!")
    $("#total").text("Total con descuento")
    $("#reprice").text(total+"€")
    $("#price").hide()
  }
  //ensure non-negative total

});





