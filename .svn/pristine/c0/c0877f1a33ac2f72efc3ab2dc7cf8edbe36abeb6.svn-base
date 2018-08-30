package cn.com.cdboost.collect.controller;

import cn.com.cdboost.collect.service.WebServiceInterfaceService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/webService")
public class WebServiceController {

	@Autowired
	private WebServiceInterfaceService webServiceInterfaceService;

	// 系统首页
	@RequestMapping("/regeditMote")
	@ResponseBody
	public String RegeditMote(JSONObject object) throws Exception {
		return webServiceInterfaceService.RegeditMote(object);
	}

	@RequestMapping("/delMote")
	@ResponseBody
	public String first(String moteEUI, String cusID) {
		return webServiceInterfaceService.DelMote(moteEUI, cusID);
	}

}
