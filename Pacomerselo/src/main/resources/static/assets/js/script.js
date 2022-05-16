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



$( "#coupon" ).change( function() {
  let price = parseInt($("#price").val())
  let coupon = String($("#coupon").val())
  let found=false;

  if(free.includes(coupon)){
    percentDiscount=0
    found=true
  }
  else{
    if(percent10.includes(coupon)){
      percentDiscount=.9
      found=true
    }
  else{
    if(percent20.includes(coupon)){
      percentDiscount=.8
      found=true
    }
  }
  }

  //adjust dollar-value variable if the coupon code entered matches
  if(dollars20.includes(coupon)){
    priceDiscount=20
    found=true
  }
  else{
    if(dollars25.includes(coupon)){
      priceDiscount=25
      found=true
    }
  }

  if(shipping.includes(coupon)){
    priceDiscount=5
    found=true
    $("#shipping").text("Gastos de envío gratis")
  }

  let total = price*percentDiscount-priceDiscount

  if(!found){
    $("#err").text("Cupón inválido")
    $("#message").text("")
  }else{
    $("#err").text("")
    $("#message").text("Cupón aplicado!")
  }
  //ensure non-negative total
  if(total < 0){total = 0}

  $("#newprice").val(total)
  $("#form").submit()
});

$("#form").change( function() {
  $("#form").submit();
});





