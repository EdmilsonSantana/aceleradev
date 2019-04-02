package challenge;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(name="QuoteServlet", urlPatterns= {"/v1/quote/*"}, loadOnStartup=1)
public class QuoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private QuoteDao dao;

	public QuoteServlet() {
		this.dao = new QuoteDao();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Quote quote;
			String pathInfo = req.getPathInfo();
			if (pathInfo == null || pathInfo.equals("/")) {
				quote = dao.getQuote();
			} else {
				String actor = pathInfo.split("/")[1];
				quote = dao.getQuoteByActor(actor);
			}

			PrintWriter out = resp.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			
			out.print(mapper.writeValueAsString(quote));
			out.close();
			
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
