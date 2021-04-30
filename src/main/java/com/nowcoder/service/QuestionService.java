package com.nowcoder.service;

import com.nowcoder.dao.QuestionDAO;
import com.nowcoder.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author hc
 */
@Service
public class QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    SensitiveService sensitiveService;

    public int addQuestion(Question question) {
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        // 敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        return questionDAO.addQuestion(question) > 0 ? question.getId() : 0;
    }

    public Question getById(int id) {
        return questionDAO.getById(id);
    }

    public List<Question> getLatestQuestions(int offset, int limit) {
        return questionDAO.getLatestQuestions(offset, limit);
    }

    public List<Question> getUserLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.getUserLatestQuestions(userId, offset, limit);
    }
}
