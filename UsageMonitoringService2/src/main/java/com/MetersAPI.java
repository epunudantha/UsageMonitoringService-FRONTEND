package com;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//for map
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Servlet implementation class MetersAPI
 */
@WebServlet("/MetersAPI")
public class MetersAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Meter meterObj = new Meter();

	//convert request parameters to a map
	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();

		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();

			String[] params = queryString.split("&");

			for (String param : params) {
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		} catch (Exception e) {

		}

		return map;
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MetersAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//sending values to insert function
		String output = meterObj.insertMeter(request.getParameter("meterCode"),
											 request.getParameter("houseownerName"),
											 request.getParameter("singleUnitPrice"),
											 request.getParameter("unitsAmount"));
                                             request.getParameter("totalPrice");
											
		//sending the output to client
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//parameter map
		Map paras = getParasMap(request);

		//getting values from the map and sending to update function
		String output = meterObj.updateMeter(paras.get("hidMeterIDSave").toString(),
											 paras.get("meterCode").toString(),
											 paras.get("houseownerName").toString(),
											 paras.get("singleUnitPrice").toString(),
											 paras.get("unitsAmount").toString());
                                             paras.get("totalPrice").toString();
		
		//sending the output to client
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//parameter map
		Map paras = getParasMap(request);

		//getting values from the map and sending to delete function
		String output = meterObj.deleteMeter(	paras.get("meterID").toString());
		
		//sending the output to client
		response.getWriter().write(output);
	}

}