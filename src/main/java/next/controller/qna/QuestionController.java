package next.controller.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import core.web.argumentresolver.LoginUser;
import next.CannotOperateException;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;
import next.service.QnaService;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private QnaService qnaService;

	@RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
	public String show(@PathVariable long questionId, Model model) throws Exception {
		Question question = qnaService.findById(questionId);
		if (question.isDeleted()) {
			throw new IllegalAccessException("삭제된 게시물입니다.");
		}
		model.addAttribute("question", qnaService.findById(questionId));
		model.addAttribute("answers", qnaService.findAllByQuestionId(questionId));
		return "/qna/show";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createForm(@LoginUser User loginUser, Model model) throws Exception {
		if (loginUser.isGuestUser()) {
			return "redirect:/users/loginForm";
		}
		model.addAttribute("question", new Question());
		model.addAttribute("isUpdate", false);
		return "/qna/form";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String create(@LoginUser User loginUser, Question question) throws Exception {
		if (loginUser.isGuestUser()) {
			return "redirect:/users/loginForm";
		}
		questionDao.insert(question.newQuestion(loginUser));
		return "redirect:/";
	}

	@RequestMapping(value = "/{questionId}/edit", method = RequestMethod.GET)
	public String editForm(@LoginUser User loginUser, @PathVariable long questionId, Model model) throws Exception {
		Question question = qnaService.findById(questionId);
		if (!question.isSameUser(loginUser)) {
			throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
		}
		model.addAttribute("question", question);
		model.addAttribute("isUpdate", true);
		return "/qna/form";
	}

	@RequestMapping(value = "/{questionId}", method = RequestMethod.PUT)
	public String edit(@LoginUser User loginUser, @PathVariable long questionId, Question editQuestion) throws Exception {
		qnaService.updateQuestion(questionId, editQuestion, loginUser);
		return "redirect:/";
	}

	@RequestMapping(value = "/{questionId}", method = RequestMethod.DELETE)
	public String delete(@LoginUser User loginUser, @PathVariable long questionId, Model model) throws Exception {
		try {
			qnaService.deleteQuestion(questionId, loginUser);
			return "redirect:/";
		} catch (CannotOperateException e) {
			model.addAttribute("question", qnaService.findById(questionId));
			model.addAttribute("errorMessage", e.getMessage());
			return "show";
		}
	}
}
