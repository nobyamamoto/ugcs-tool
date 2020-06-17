package example.ugcs.resttool.presentation

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@Controller
class IndexController {
  @GetMapping( "/" )
  fun home() : ModelAndView{
      return ModelAndView("forward:/index.html")
  }
}
