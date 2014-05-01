
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * Servlet implementation class DataServlet
 */
@WebServlet("/DataServlet")
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public DataServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpClient client = new DefaultHttpClient();
		
		CookieStore cookieStore = new BasicCookieStore(); 
		BasicClientCookie cookie = new BasicClientCookie("sessionid", "7ebef8e2767298abfa35569a8401f06c90a06542c42efc006390e56917dd05f780035630c2f345d659692ad62ad56350a2db6497ac0a9cbde40c215723f5cde3");
		cookie.setDomain(".com");
		cookie.setPath("/");
		cookieStore.addCookie(cookie); 
		
		
		HttpContext localContext = new BasicHttpContext();
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		
		
		  HttpGet request1 = new HttpGet("http://triton-alpha.ebay.com:8080/2.2/user/carts");
		  HttpResponse response1 = client.execute(request1,localContext);
		  BufferedReader rd = new BufferedReader (new InputStreamReader(response1.getEntity().getContent()));
		  String line = "";
		  while ((line = rd.readLine()) != null) {
		    System.out.println(line);
		  }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
