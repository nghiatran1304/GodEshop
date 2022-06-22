package com.godEShop.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.godEShop.Dao.ProductPhotoDAO;
import com.godEShop.Dto.AccessoryDto;
import com.godEShop.Dto.ProductDiscountDto;
import com.godEShop.Dto.ProductShopDto;
import com.godEShop.Dto.WatchDto;
import com.godEShop.Entity.MailInfo;
import com.godEShop.Entity.ProductPhoto;
import com.godEShop.Entity.User;
import com.godEShop.Service.BrandService;
import com.godEShop.Service.CategoryService;
import com.godEShop.Service.MailerService;
import com.godEShop.Service.ProductService;
import com.godEShop.Service.UserService;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    @Autowired
    ProductPhotoDAO productPhotoDAO;

    @GetMapping("/product")
    public String productPage1(Model model, @RequestParam("p") Optional<Integer> p,
	    @RequestParam("keywords") Optional<String> kw, @RequestParam("sort") Optional<String> sort,
	    @RequestParam("search-category") Optional<String> cName,
	    @RequestParam("search-brand") Optional<String> bName, @RequestParam("minPrice") Optional<Double> minPrice,
	    @RequestParam("maxPrice") Optional<Double> maxPrice, @RequestParam("isGender") Optional<Integer> isGender) {

	// top 2 seller
	List<ProductDiscountDto> lstTopSeller = new ArrayList<>();
	for (int i = 0; i < 3; i++) {
	    lstTopSeller.add(productService.productBestSeller().get(i));
	}
	model.addAttribute("lstTopSeller", lstTopSeller);

	// ---------------
	if (p.isPresent() && p.get() <= 0) {
	    p = Optional.of(0);
	}
	Pageable pageable = PageRequest.of(p.orElse(0), 12);

	if (!sort.isPresent()) {
	    pageable = PageRequest.of(p.orElse(0), 12);
	    model.addAttribute("sortChose", "a");
	} else if (sort.get().equalsIgnoreCase("discount")) {
	    pageable = PageRequest.of(p.orElse(0), 12, JpaSort.unsafe("pd.discount").descending());
	    model.addAttribute("sortChose", "b");
	    model.addAttribute("sortSelected", sort.get());
	} else if (sort.get().equalsIgnoreCase("rating")) {
	    pageable = PageRequest.of(p.orElse(0), 12, JpaSort.unsafe("CAST(AVG(pe.evaluation) AS int)").descending());
	    model.addAttribute("sortChose", "c");
	    model.addAttribute("sortSelected", sort.get());
	} else if (sort.get().equalsIgnoreCase("newest")) {
	    pageable = PageRequest.of(p.orElse(0), 12, JpaSort.unsafe("p.createDate").descending());
	    model.addAttribute("sortChose", "d");
	    model.addAttribute("sortSelected", sort.get());
	} else if (sort.get().equalsIgnoreCase("lowtohigh")) {
	    pageable = PageRequest.of(p.orElse(0), 12, JpaSort.unsafe("p.price").ascending());
	    model.addAttribute("sortChose", "e");
	    model.addAttribute("sortSelected", sort.get());
	} else if (sort.get().equalsIgnoreCase("hightolow")) {
	    pageable = PageRequest.of(p.orElse(0), 12, JpaSort.unsafe("p.price").descending());
	    model.addAttribute("sortChose", "f");
	    model.addAttribute("sortSelected", sort.get());
	}

	String kwords = "";
	if (kw.isPresent()) {
	    kwords = kw.get();
	}

	String categoryName = "";
	if (cName.isPresent()) {
	    categoryName = cName.get();
	}
	model.addAttribute("cateName", categoryName);

	String brandName = "";
	if (bName.isPresent()) {
	    brandName = bName.get();
	}
	model.addAttribute("brandName", brandName);

	/* search By Price */
	Double sminPrice = minPrice.isPresent() ? minPrice.get() : 1.0;
	Double smaxPrice = maxPrice.isPresent() ? maxPrice.get() : 1000000.0;

	if (sminPrice > smaxPrice) {
	    model.addAttribute("mPrice", "Please input valid price range");
	    sminPrice = 1.0;
	    smaxPrice = 1000000.0;

	}
	model.addAttribute("sminPrice", sminPrice);
	model.addAttribute("smaxPrice", smaxPrice);

	int i6 = !isGender.isPresent() ? 4 : isGender.get();

	if (model.getAttribute("gender") == null) {
	    model.addAttribute("gender", i6);
	}

	int i7 = 4, i8 = 4, i9 = 4;

	if (i6 == 0) {
	    i7 = 1;
	    i8 = 2;
	    i9 = i8;
	} else if (i6 == 1) {
	    i7 = 0;
	    i8 = 2;
	    i9 = i8;
	} else if (i6 == 2) {
	    i7 = 0;
	    i8 = 1;
	    i9 = i8;
	}

	model.addAttribute("gender", i6);

	String s1 = kwords.length() == 0 ? "" : " > " + kwords;
	String s2 = categoryName.length() == 0 ? "" : " > " + categoryName;
	String s3 = brandName.length() == 0 ? "" : " > " + brandName;

	model.addAttribute("nameOfSearch", (s1 + s2 + s3));

	Page<ProductShopDto> page = productService.productShop("%" + kwords + "%", "%" + categoryName + "%",
		"%" + brandName + "%", sminPrice, smaxPrice, i6, i7, i8, i9, pageable);

	Date d = new Date();

	model.addAttribute("page", page);

	model.addAttribute("timeNow", d);

	model.addAttribute("totalProducts", page.getTotalElements());
	model.addAttribute("toProduct", page.getNumber());
	model.addAttribute("totalPage", page.getPageable().getPageSize());

	return "product/product";
    }

    @GetMapping("/product/{id}")
    public String singleproductPage(Model model, @PathVariable("id") Long id) {

	Date d = new Date();

	model.addAttribute("timeNow", d);

	List<ProductPhoto> lstPhotoByProductId = productPhotoDAO.getAllProductPhotoByProductId(id);
	model.addAttribute("lstPhotoByProductId", lstPhotoByProductId);

	List<ProductShopDto> pItems = productService.productShopById(id);
	ProductShopDto productItem = pItems.get(pItems.size() - 1);
//	ProductShopDto productItem = productService.productShopById(id);
	model.addAttribute("productItem", productItem);

	if (productItem.getProductCategoryId() == 13) {
	    AccessoryDto accessoryDto = productService.getAccessoryDtoById(id);
	    model.addAttribute("watchDetail", accessoryDto);
	    model.addAttribute("isWatch", false);
	} else {
	    WatchDto watchDto = productService.getWatchById(id);
	    model.addAttribute("watchDetail", watchDto);
	    model.addAttribute("isWatch", true);
	}
	List<ProductDiscountDto> lstRd = new ArrayList<>();
	int maxLength = productService.productByIdBrands(productItem.getProductCategoryId()).size() > 5 ? 5
		: productService.productByIdBrands(productItem.getProductCategoryId()).size();
	for (int i = 0; i < maxLength; i++) {
	    lstRd.add(productService.productByIdBrands(productItem.getProductCategoryId()).get(i));
	}
	model.addAttribute("lstRelateProduct", lstRd);

	return "product/single-product";
    }

    @Autowired
    MailerService mailerServie;

    @Autowired
    ServletContext application;

    @Autowired
    HttpServletRequest req;

    @Autowired
    UserService userService;

    @PostMapping("/sendmail/send")
    public String sendMail(Model model, @RequestParam("body") Optional<String> body,
	    @RequestParam("attachments") MultipartFile[] attachments) {
	User u = userService.findByAccountUsername(req.getRemoteUser());
	MailInfo m = new MailInfo();
	m.setFrom(u.getEmail());
	m.setSubject("Feedback Product");
	m.setTo("testemailnghiatran@gmail.com");

	m.setBody(body.get());

	String listAttachments = "";
	// int st = 0;
	for (MultipartFile file : attachments) {
	    try {
		File dir = new File("src\\main\\resources\\static\\upload\\EmailFiles");
		String absolutePath = dir.getAbsolutePath().toString();
		System.out.println(" >> Path: " + absolutePath);
		String s = System.currentTimeMillis() + file.getOriginalFilename();
		String name = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
		File savedFile = new File(dir.getAbsolutePath(), name);
		System.out.println(" >> File just saved: " + savedFile.getAbsolutePath());
		file.transferTo(savedFile);
		listAttachments += savedFile.getAbsolutePath() + " ";
		/*
		 * Path path = Paths.get(application.getRealPath("\\") + "\\uploads\\");
		 * InputStream ips = file.getInputStream(); Files.copy(ips,
		 * path.resolve(file.getOriginalFilename()),
		 * StandardCopyOption.REPLACE_EXISTING); String realPathFileUpload =
		 * application.getRealPath("\\") + "\\uploads\\" + file.getOriginalFilename();
		 * System.out.println(realPathFileUpload); listAttachments +=
		 * realPathFileUpload.toString() + " "; st++;
		 */
	    } catch (Exception e) {
		System.out.println("save() : " + e.getMessage());
	    }
	}

	m.setAttachments(listAttachments.split(" "));

	try {
	    mailerServie.send(m);
	} catch (Exception e) {
	    System.out.println("Error : " + e.getMessage());
	}
	return "redirect:/product";
    }

}
