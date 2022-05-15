/* Theme JS */

(function ($) {
  "use strict";

  /* ----------------------------------------------
        jQuery MeanMenu
    ---------------------------------------------- */
  $("#mobile-menu-active").meanmenu({
    meanScreenWidth: "991",
    meanMenuContainer: ".mobile-menu-area .mobile-menu",
  });

  /* ----------------------------------------------
        nice-select-menu
    ---------------------------------------------- */
  $(".nice-select-menu").niceSelect();
  /*----------------------------
    4.1 Vertical-Menu Activation
    -----------------------------*/
  $(".categorie-title").on("click", function () {
    $(".categori-menu-list").slideToggle();
  });

  /* ----------------------------------------------
        gallery-post active
    ---------------------------------------------- */
  $(".gallery-post").owlCarousel({
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    loop: true,
    dots: false,
    nav: false,
    navText: [
      "<i class='fa fa-chevron-left'></i>",
      "<i class='fa fa-chevron-right'></i>",
    ],
    item: 1,
    responsive: {
      0: {
        items: 1,
      },
      768: {
        items: 1,
      },
      1000: {
        items: 1,
      },
    },
  });

  /* ----------------------------------------------
        product-carousel-active
    ---------------------------------------------- */
  $(".product-carousel-active").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 2,
    responsive: {
      0: {
        items: 1,
      },
      768: {
        items: 1,
      },
      992: {
        items: 1,
      },
      1170: {
        items: 1,
      },
      1366: {
        items: 2,
      },
    },
  });
  /* ----------------------------------------------
        product-carousel-active
    ---------------------------------------------- */
  $(".product-carousel-active-home-four").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 1,
    responsive: {
      0: {
        items: 1,
      },
      768: {
        items: 1,
      },
      992: {
        items: 1,
      },
      1000: {
        items: 1,
      },
    },
  });

  /* ----------------------------------------------
        product-carousel-active
    ---------------------------------------------- */
  $(".home-three-product-carousel").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 1,
    responsive: {
      0: {
        items: 1,
      },
      768: {
        items: 1,
      },
      992: {
        items: 1,
      },
      1000: {
        items: 3,
      },
      1600: {
        items: 1,
      },
    },
  });

  /* ----------------------------------------------
        product-carousel-active
    ---------------------------------------------- */
  $(".product-carousel-active-2").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 5,
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
      },
      768: {
        items: 3,
      },
      992: {
        items: 3,
      },
      1000: {
        items: 4,
      },
      1366: {
        items: 5,
      },
    },
  });
  /* ----------------------------------------------
        product-carousel-active
    ---------------------------------------------- */
  $(".product-carousel-active-h2").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 5,
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
      },
      768: {
        items: 3,
      },
      992: {
        items: 3,
      },
      1000: {
        items: 4,
      },
      1366: {
        items: 4,
      },
      1600: {
        items: 5,
      },
    },
  });
  /* ----------------------------------------------
        product-carousel-active
    ---------------------------------------------- */
  $(".product-carousel-active-3").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 6,
    responsive: {
      0: {
        items: 1,
      },
      768: {
        items: 3,
      },
      950: {
        items: 3,
      },
      1000: {
        items: 4,
      },
      1366: {
        items: 5,
      },
      1600: {
        items: 5,
      },
    },
  });

  /* ----------------------------------------------
        product-carousel-active
    ---------------------------------------------- */
  $(".product-carousel-active-4").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 1,
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
      },
      600: {
        items: 2,
      },
      1000: {
        items: 2,
      },
    },
  });
  /* ----------------------------------------------
        best seller carousel active
    ---------------------------------------------- */
  $(".bestseller-sidebar").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 1,
    responsive: {
      0: {
        items: 1,
      },
      600: {
        items: 1,
      },
      768: {
        items: 2,
      },
      992: {
        items: 3,
      },
      1000: {
        items: 3,
      },
      1170: {
        items: 3,
      },
      1366: {
        items: 1,
      },
    },
  });
  /* ----------------------------------------------
        best seller carousel active
    ---------------------------------------------- */
  $(".newarival-sidebar").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 1,
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
      },
      600: {
        items: 2,
      },
      992: {
        items: 3,
      },
      1000: {
        items: 3,
      },
      1170: {
        items: 3,
      },
      1366: {
        items: 1,
      },
    },
  });
  /* ----------------------------------------------
        testimonial carousel active
    ---------------------------------------------- */
  $(".testimonial-sidebar").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 1,
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
      },
      600: {
        items: 1,
      },
      1000: {
        items: 1,
      },
    },
  });
  /* ----------------------------------------------
        mini product carousel active
    ---------------------------------------------- */
  $(".mini-product").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 1,
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
      },
      600: {
        items: 1,
      },
      992: {
        items: 1,
      },
      1000: {
        items: 1,
      },
      1170: {
        items: 1,
      },
    },
  });
  /* ----------------------------------------------
        mini product carousel active
    ---------------------------------------------- */
  $(".mini-product-2").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 1,
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
      },
      600: {
        items: 1,
      },
      768: {
        items: 2,
      },
      992: {
        items: 3,
      },
      1000: {
        items: 3,
      },
      1170: {
        items: 3,
      },
      1366: {
        items: 1,
      },
    },
  });

  /* ----------------------------------------------
        product-carousel-active
    ---------------------------------------------- */
  $(".home-two-product-carousel-active").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 4,
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
      },
      768: {
        items: 2,
      },
      950: {
        items: 3,
      },
      1000: {
        items: 4,
      },
    },
  });

  /* ----------------------------------------------
       Home two sidebar product-carousel-active
    ---------------------------------------------- */
  $(".home-two-sidebar-product").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 1,
    dots: false,
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
      },
      768: {
        items: 2,
      },
      950: {
        items: 3,
      },
      1000: {
        items: 3,
      },
    },
  });
  /* ----------------------------------------------
        brand-carousel-active
    ---------------------------------------------- */
  $(".brand-carousel-active").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: true,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 8,
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
        center: true,
      },
      768: {
        items: 4,
      },
      1000: {
        items: 8,
      },
    },
  });
  /* ----------------------------------------------
        brand-carousel-active
    ---------------------------------------------- */
  $(".four-brand-carousel-active").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: true,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 6,
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
        center: true,
      },
      768: {
        items: 3,
      },
      1000: {
        items: 6,
      },
    },
  });
  /* ----------------------------------------------
        product dec slider qui active
    ---------------------------------------------- */
  $(".product-dec-slider-qui").owlCarousel({
    loop: true,
    nav: false,
    navText: [
      "<i class='ion-ios-arrow-left'></i>",
      "<i class='ion-ios-arrow-right'></i>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 4,
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
        center: true,
      },
      768: {
        items: 3,
      },
      1000: {
        items: 4,
      },
    },
  });

  $(".product-dec-slider").slick({
    dots: true,
    vertical: true,
    slidesToShow: 4,
    slidesToScroll: 4,
    verticalSwiping: true,
    arrows: true,
    nextArrow: '<i class="fa fa-chevron-down"></i>',
    prevArrow: '<i class="fa fa-chevron-up"></i>',
  });

  /* ----------------------------------------------
        slider-carousel-active
    ---------------------------------------------- */
  $(".banner-call-to-action-carousel-active").owlCarousel({
    loop: true,
    nav: true,
    navText: [
      "<img src='images/icons/arrow-left.png'>",
      "<img src='images/icons/arrow-right.png'>",
    ],
    autoplay: false,
    autoplayTimeout: 5000,
    animateOut: "fadeOut",
    animateIn: "fadeIn",
    item: 1,
    responsiveClass: true,
    responsive: {
      0: {
        items: 1,
      },
      768: {
        items: 1,
      },
      1000: {
        items: 1,
      },
    },
  });

  /* ----------------------------------------------
        slider-carousel-active
    ---------------------------------------------- */

  /* ********************************************
        5. Countdown
    ******************************************** */
  $("[data-countdown]").each(function () {
    var $this = $(this),
      finalDate = $(this).data("countdown");
    $this.countdown(finalDate, function (event) {
      $this.html(
        event.strftime(
          '<span class="cdown days"><span class="time-count">%-D</span><span>D : </span></span> <span class="cdown hour"><span class="time-count">%-H</span><span>H : </span></span> <span class="cdown minutes"><span class="time-count">%M</span><span>M : </span></span> <span class="cdown second"> <span><span class="time-count">%S</span><span>S</span></span>'
        )
      );
    });
  });

  /* ----------------------------------------------
        product popup
    ---------------------------------------------- */
  $(".product-popup").magnificPopup({
    delegate: "a", // child items selector, by clicking on it popup will open
    type: "image",
    // other options
  });

  /*--------------------------
    tab active
    ---------------------------- */
  $(".product-details-small a").on("click", function (e) {
    e.preventDefault();

    var $href = $(this).attr("href");

    $(".product-details-small a").removeClass("active");
    $(this).addClass("active");

    $(".product-details-large .tab-pane").removeClass("active");
    $(".product-details-large " + $href).addClass("active");
  });

  /* ----------------------------------------------
        Tooltip
    ---------------------------------------------- */
  $('[rel="tooltip"]').tooltip();

  /* ********************************************
        13. Cart Plus Minus Button
    ******************************************** */
  $(".cart-plus-minus").prepend('<div class="dec qtybutton">-</div>');
  $(".cart-plus-minus").append('<div class="inc qtybutton">+</div>');
  $(".qtybutton").on("click", function () {
    var $button = $(this);
    var oldValue = $button.parent().find("input").val();
    if ($button.text() == "+") {
      var newVal = parseFloat(oldValue) + 1;
    } else {
      // Don't allow decrementing below zero
      if (oldValue > 0) {
        var newVal = parseFloat(oldValue) - 1;
      } else {
        newVal = 0;
      }
    }
    $button.parent().find("input").val(newVal);
  });

  /*-- Price Range --*/
  $("#price-range").slider({
    range: true,
    min: 0,
    max: 700,
    values: [0, 700],
    slide: function (event, ui) {
      $(".price-amount").val("$" + ui.values[0] + " - $" + ui.values[1]);
    },
  });
  $(".price-amount").val(
    "$" +
      $("#price-range").slider("values", 0) +
      " - $" +
      $("#price-range").slider("values", 1)
  );
  $(".product-filter-toggle").on("click", function () {
    $(".product-filter-wrapper").slideToggle();
  });

  /* ---------------------------
    11. FAQ Accordion Active
    * ---------------------------*/
  $(".panel-heading a").on("click", function () {
    $(".panel-default").removeClass("show");
    $(this).parents(".panel-default").addClass("show");
  });

  /* CounterUp Active */


  $(".counter").counterUp({
    delay: 10, /* The delay in milliseconds per number count up */
    time: 1000, /*The total duration of the count up animation */
    offset: 100, 
    /*The viewport percentile from which the counter starts (by default it's 100, meaning it's triggered at the very moment the element enters the viewport) */
  });


  /*--
        Isotop with ImagesLoaded
    -----------------------------------*/
  var isotopFilter = $(".isotop-filter");
  var isotopGrid = $(".isotop-grid");
  var isotopGridMasonry = $(".isotop-grid-masonry");
  var isotopGridItem = ".isotop-item";
  /*-- Images Loaded --*/
  isotopGrid.imagesLoaded(function () {
    /*-- Filter List --*/
    isotopFilter.on("click", "button", function () {
      isotopFilter.find("button").removeClass("active");
      $(this).addClass("active");
      var filterValue = $(this).attr("data-filter");
      isotopGrid.isotope({ filter: filterValue });
    });
    /*-- Filter Grid Layout FitRows --*/
    isotopGrid.isotope({
      itemSelector: isotopGridItem,
      layoutMode: "fitRows",
      masonry: {
        columnWidth: 1,
      },
    });
    /*-- Filter Grid Layout Masonary --*/
    isotopGridMasonry.isotope({
      itemSelector: isotopGridItem,
      layoutMode: "masonry",
      masonry: {
        columnWidth: 1,
      },
    });
  });

  /*-- Image --*/
  var imagePopup = $(".image-popup");
  imagePopup.magnificPopup({
    type: "image",
  });

  $('iframe[src*="youtube"]').parent().fitVids();

  /*--------------------------
     ScrollUp
    ---------------------------- */
  $.scrollUp({
    scrollText: '<i class="ion-arrow-up-c"></i>',
    easingType: "linear",
    scrollSpeed: 900,
    animation: "fade",
  });

  // -------------------------------------------------------------
  // nivoSlider
  // -------------------------------------------------------------

  $.fn.elExists = function () {
    return this.length > 0;
  };

  if ($("#mainSlider").elExists()) {
    $("#mainSlider").nivoSlider({
      manualAdvance: false,
      directionNav: true,
      animSpeed: 500,
      slices: 18,
      pauseTime: 5000,
      pauseOnHover: false,
      controlNav: false,
      prevText: '<i class="fa fa-angle-left nivo-prev-icon"></i>',
      nextText: '<i class="fa fa-angle-right nivo-next-icon"></i>',
    });
  }
})(jQuery);
