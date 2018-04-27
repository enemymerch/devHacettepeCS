package controller;



import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RecordController
 */
@WebServlet("/RecordController/*")
public class RecordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordController() {
    	super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("../RecordPages/RecordEdit.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getPathInfo().equals("/Add")){
			AddRecord(request);
			response.sendRedirect("../RecordPages/RecordEdit.jsp");
		}else if(request.getPathInfo().equals("/Edit")){
			EditRecord(request);
			response.sendRedirect("../RecordPages/RecordEdit.jsp");
		}else if(request.getPathInfo().equals("/Delete")){
			DeleteRecord(request);
			response.sendRedirect("../RecordPages/RecordEdit.jsp");
		}else{
			doGet(request, response);
		}
	}
	
	protected void AddRecord(HttpServletRequest request){
		
		
		Record newRecord = new Record((String)request.getParameter("id"), (String)request.getParameter("title"), 
								(String)request.getParameter("location"), (String)request.getParameter("description")
								, (String)request.getParameter("startDate"), (String)request.getParameter("endDate"));
		
		HttpSession session = request.getSession();
		ServletContext cntx = getServletContext();
		
		ArrayList<Record> sessionScopeRecordList = new ArrayList<Record>();
		ArrayList<Record> appScopeRecordList = new ArrayList<Record>();
		

		if(session.getAttribute("RecordList")!=null){
			sessionScopeRecordList = (ArrayList<Record>)session.getAttribute("RecordList");
		}
		if(cntx.getAttribute("RecordList")!=null){
			appScopeRecordList = (ArrayList<Record>)cntx.getAttribute("RecordList");
		}

		if(!isRecordExits(newRecord, sessionScopeRecordList, appScopeRecordList)){
			sessionScopeRecordList.add(newRecord);
			session.setAttribute("addMessage", "New record is added to the system!");
		}else{
			session.setAttribute("addMessage", "New record already exits!");
		}
		
		session.setAttribute("RecordList", sessionScopeRecordList);
		
	}
	
	protected void EditRecord(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		ServletContext cntx = getServletContext();
		
		ArrayList<Record> sessionScopeRecordList = new ArrayList<Record>();
		ArrayList<Record> appScopeRecordList = new ArrayList<Record>();
		

		if(session.getAttribute("RecordList")!=null){
			sessionScopeRecordList = (ArrayList<Record>)session.getAttribute("RecordList");
		}
		if(cntx.getAttribute("RecordList")!=null){
			appScopeRecordList = (ArrayList<Record>)cntx.getAttribute("RecordList");
		}
		
		// Now let's see if the record exits!
		String id = request.getParameter("ID");
		
		if(!isRecordExits(id, sessionScopeRecordList, appScopeRecordList)){// is there such a record with that id ?
			session.setAttribute("editMessage", "There's no record with that ID!");// No !
		}else{
			
			 Record tempRec = new Record();
			 Boolean isRecordShared = false;
			 int recordIndex = -1;
			
			 // Yes, there's and let's find it!
			 // Let's check applicationScopeRecordList
			 for(int i=0;i<appScopeRecordList.size(); i++){
				 if(id.equals(appScopeRecordList.get(i).getID())){ // did we found it ? 
					 isRecordShared = true;
					 recordIndex = i;
					 tempRec = appScopeRecordList.get(i);
					 break;
				 }
			 }
			// nope, it's not in applicationScope
			// Let's check sessionScopeRecordList
			for(int i=0;i<sessionScopeRecordList.size(); i++){
				if(id.equals(sessionScopeRecordList.get(i).getID())){ // did we found it ? 
					 isRecordShared = false;
					 recordIndex = i;
					 tempRec = sessionScopeRecordList.get(i);
					 break;
				}
			}
			
			String title = request.getParameter("title");
			String location = request.getParameter("location");
			String description = request.getParameter("description");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			// Now we're going to change the information of the record!
			if(!title.equals("")){ // NAME
				tempRec.setTitle(title);
			}
			if(!location.equals("")){ //SURNAME
				tempRec.setLocation(location);
			}
			if(!description.equals("")){  // PHONE
				tempRec.setDescription(description);
			}
			if(!startDate.equals("")){  // ADDRESS
				tempRec.setStartTime(startDate);
			}
			if(!endDate.equals("")){  // AGE
				tempRec.setEndTime(endDate);
			}
			
			
			if(isRecordShared){
				// in appScope
				appScopeRecordList.set(recordIndex, tempRec);
			}else{
				// in sessionScpoe
				sessionScopeRecordList.set(recordIndex, tempRec);
			}
			
			
			
			/*Now , goint to check if we're going to share the record or not ?*/
			boolean SharedCheckBox = request.getParameter( "shared" ) != null;
			
			if(SharedCheckBox != isRecordShared){
				// Now, need to change the scope of record
				if(isRecordShared == false){// record in sessionScope
					// Going to remove the record from the sessionScpoe
					// And add it into the appScope
					sessionScopeRecordList.remove(recordIndex);//Removed!
					appScopeRecordList.add(tempRec);
				}else{ // record in appScope
					// Going to remove the record from the AppScope
					// And add it into the sessionScope
					appScopeRecordList.remove(recordIndex);
					sessionScopeRecordList.add(tempRec);
				}
			}
			
		}
		// Going to set RecordLists!
		session.setAttribute("RecordList", sessionScopeRecordList);
		cntx.setAttribute("RecordList", appScopeRecordList);
	}
	
	protected void DeleteRecord(HttpServletRequest request){
		
		String id = request.getParameter("ID");
		
		HttpSession session = request.getSession();
		ServletContext cntx = getServletContext();
		
		ArrayList<Record> sessionScopeRecordList = new ArrayList<Record>();
		ArrayList<Record> appScopeRecordList = new ArrayList<Record>();
		
		if(session.getAttribute("RecordList")!=null){
			sessionScopeRecordList = (ArrayList<Record>)session.getAttribute("RecordList");
		}
		if(cntx.getAttribute("RecordList")!=null){
			appScopeRecordList = (ArrayList<Record>)cntx.getAttribute("RecordList");
		}
		
		if(!isRecordExits(id, sessionScopeRecordList, appScopeRecordList)){// is there such record ?
			session.setAttribute("deleteMessage", "There's no such record with that ID!");// no, there's not such a record
		}else{
			// Yes, there's and let's find it!
			
			// Let's check applicationScopeRecordList
			for(int i=0;i<appScopeRecordList.size(); i++){
				if(id.equals(appScopeRecordList.get(i).getID())){ // did we found it ? 
					appScopeRecordList.remove(i);// yea, delete ! 
					cntx.setAttribute("RecordList", appScopeRecordList);// Set appScopeList !
					session.setAttribute("deleteMessage", "Record with id: "+ id + " is deleted!");
					return;// set the message and return!
				}
			}
			// nope, it's not in applicationScope
			// Let's check sessionScopeRecordList
			for(int i=0;i<sessionScopeRecordList.size(); i++){
				if(id.equals(sessionScopeRecordList.get(i).getID())){ // did we found it ? 
					sessionScopeRecordList.remove(i);// yea, delete ! 
					session.setAttribute("RecordList", sessionScopeRecordList);// Set sessionScopeList! 
					session.setAttribute("deleteMessage", "Record with id: "+ id + " is deleted!");
					return;// set the message and return!
				}
			}
		}
		
		
	}
	
	protected boolean isRecordExits(Record rec, ArrayList<Record> sessionScopeList, ArrayList<Record> appScopeList){
		for(Record r:sessionScopeList){
			if(r.getID().equals(rec.getID())){
				return true;
			}
		}
		
		for(Record r:appScopeList){
			if(r.getID().equals(rec.getID())){
				return true;
			}
		}
		return false;
	}
	
	
	
	protected boolean isRecordExits(String id, ArrayList<Record> sessionScopeList, ArrayList<Record> appScopeList){
		for(Record r:sessionScopeList){
			if(r.getID().equals(id)){
				return true;
			}
		}
		
		for(Record r:appScopeList){
			if(r.getID().equals(id)){
				return true;
			}
		}
		return false;
	}


}



