package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("")
@RestController
public class HomeController {

	@GetMapping
	public String homePage() {
		return """
				<p><br></p>
				<pre>
				    ███████╗██╗██╗     ███╗   ███╗ ██████╗     ██████╗  █████╗ ████████╗███████╗
				    ██╔════╝██║██║     ████╗ ████║██╔═══██╗    ██╔══██╗██╔══██╗╚══██╔══╝██╔════╝
				    █████╗  ██║██║     ██╔████╔██║██║   ██║    ██████╔╝███████║   ██║   █████╗
				    ██╔══╝  ██║██║     ██║╚██╔╝██║██║   ██║    ██╔══██╗██╔══██║   ██║   ██╔══╝
				    ██║     ██║███████╗██║ ╚═╝ ██║╚██████╔╝    ██║  ██║██║  ██║   ██║   ███████╗
				    ╚═╝     ╚═╝╚══════╝╚═╝     ╚═╝ ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
				</pre>
				""";
	}

	@GetMapping("favicon.ico")
	public String faviconIco() {
		return """
				        <p><br></p>
				""";
	}

	@GetMapping(".well-known/appspecific/com.chrome.devtools.json")
	public String devtools() {
		return """
				        <p><br></p>
				""";
	}

	@GetMapping("sw.js")
	public String swjs() {
		return """
				        <p><br></p>
				""";
	}
}
