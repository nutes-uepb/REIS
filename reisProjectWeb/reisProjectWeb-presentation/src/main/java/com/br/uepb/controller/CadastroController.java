/*
The MIT License (MIT)
Copyright (c) 2016 Núcleo de Tecnologias Estratégicas em Saúde (NUTES)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions 
of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
DEALINGS IN THE SOFTWARE. 
*/
package com.br.uepb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.br.uepb.business.GerenciarSessaoBusiness;
import com.br.uepb.business.LoginBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.model.LoginDomain;
 
@Controller
public class CadastroController {
	
	@RequestMapping(value = "/index/cadastrar.html", method = RequestMethod.GET)
	public ModelAndView cadastrarGet(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index/cadastrar");
		modelAndView.addObject("loginDomain",
				new LoginDomain());
		return modelAndView;
	}

	@RequestMapping(value = "/index/cadastrar.html", method = RequestMethod.POST)
	public ModelAndView cadastrarPost(@ModelAttribute("loginDomain") LoginDomain login, Model model, HttpSession session) {

		LoginBusiness loginBusiness = new LoginBusiness();
		ModelAndView modelAndView = new ModelAndView();
			
		if(login!=null){
			if(loginBusiness.salvar(login)){
				session.setAttribute("login", login.getLogin());
				SessaoBusiness sessao = new SessaoBusiness();
				sessao.setLoginDomain(login);
				GerenciarSessaoBusiness.addSessaoBusiness(login, sessao);
				modelAndView.setViewName("redirect:/home/home.html");
				
				modelAndView.setViewName("redirect:/home/home.html");
				String mensagem = "Cadastro realizado com sucesso";
				modelAndView.addObject("mensagem", mensagem);
				modelAndView.addObject("status", "0");
			}else{
				modelAndView.setViewName("index/cadastrar");
				String mensagem = "Login já está sendo usado";
				modelAndView.addObject("mensagem", mensagem);
				modelAndView.addObject("status", "1");
			}
		}
		return modelAndView;
	}
}
