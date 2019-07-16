package cn.edu.whu.irlab.irep;

import cn.edu.whu.irlab.irep.service.entity.Doc;
import cn.edu.whu.irlab.irep.mybatis.entity.FullIndex;
import cn.edu.whu.irlab.irep.mybatis.entity.InvertedIndex;
import cn.edu.whu.irlab.irep.mybatis.entity.Record;
import cn.edu.whu.irlab.irep.mybatis.service.impl.FullIndexServiceImpl;
import cn.edu.whu.irlab.irep.mybatis.service.impl.InvertedIndexServiceImpl;
import cn.edu.whu.irlab.irep.mybatis.service.impl.RecordServiceImpl;
import cn.edu.whu.irlab.irep.service.util.Constructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IrepApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexTest {




    @Autowired
    public RecordServiceImpl recordService;

    @Autowired
    public InvertedIndexServiceImpl invertedIndexService;

    @Autowired
    public FullIndexServiceImpl fullIndexService;


    public String folderPath = "resources/doc_ch";//待处理文档的文件夹路径

    @Test
    public void indexTest() {
        File fileFolder = new File(folderPath);
        String analyzerName = "standard";
        boolean isRemoveStopWord = true;
        HttpServletRequest request=new HttpServletRequest() {
            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String s) {
                return 0;
            }

            @Override
            public String getHeader(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaders(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String s) {
                return 0;
            }

            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public String getPathInfo() {
                return null;
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public String getRemoteUser() {
                return null;
            }

            @Override
            public boolean isUserInRole(String s) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return null;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean b) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public String changeSessionId() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            @Override
            public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
                return false;
            }

            @Override
            public void login(String s, String s1) throws ServletException {

            }

            @Override
            public void logout() throws ServletException {

            }

            @Override
            public Collection<Part> getParts() throws IOException, ServletException {
                return null;
            }

            @Override
            public Part getPart(String s) throws IOException, ServletException {
                return null;
            }

            @Override
            public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
                return null;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public long getContentLengthLong() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String s) {
                return new String[0];
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration<Locale> getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            @Override
            public String getRealPath(String s) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public AsyncContext startAsync() throws IllegalStateException {
                return null;
            }

            @Override
            public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
                return null;
            }

            @Override
            public boolean isAsyncStarted() {
                return false;
            }

            @Override
            public boolean isAsyncSupported() {
                return false;
            }

            @Override
            public AsyncContext getAsyncContext() {
                return null;
            }

            @Override
            public DispatcherType getDispatcherType() {
                return null;
            }
        };
        request.getSession().setAttribute("analyzer","smartChinese");
        request.getSession().setAttribute("removeStopWord",true);

        String indexType = Constructor.indexTypeConstructor(request);

        //向records表中插入数据
        if (fileFolder.exists()) {
            File[] files = fileFolder.listFiles();
            for (File file :
                    files) {
                Doc doc = new Doc(file.getPath(), file.getName());
                ArrayList<String> termList = doc.preProcess(analyzerName, isRemoveStopWord);
                int num = 0;
                for (String s :
                        termList) {
                    Record record = new Record();
                    record.setTerm(s);
                    record.setDocId(doc.getId());
                    record.setLocation(num);
                    record.setIndexType(indexType);
//                    //System.out.println(record.toString());
                    recordService.insert(record);
                    num++;
                }
            }
        }
    }

    @Test
    public void createInvertedIndexTest() {
        List<Record> recordList = new ArrayList<>();
        recordList = recordService.selectByIndexType("standard_remove");
//        System.out.println(recordList.size());
        Map<String, List<Record>> termId_Record = new HashMap<>();
        String termID;

        //按照term和doc_id聚合record
        for (Record r :
                recordList) {
            List<Record> records = new ArrayList<>();
            termID = r.getTerm() + r.getDocId();
            if (termId_Record.containsKey(termID)) {
                records = termId_Record.get(termID);
                records.add(r);
                termId_Record.put(termID, records);
            } else {
                records.add(r);
                termId_Record.put(termID, records);
            }
        }
//        System.out.println(termId_Record.size());
        for (String s :
                termId_Record.keySet()) {
            String locations = "";
            Record tempRecord = termId_Record.get(s).get(0);
            InvertedIndex invertedIndex = new InvertedIndex();
            invertedIndex.setDocId(tempRecord.getDocId());
            invertedIndex.setIndexType(tempRecord.getIndexType());
            invertedIndex.setTerm(tempRecord.getTerm());
            invertedIndex.setTf(termId_Record.get(s).size());
            //构造locations
            for (Record r :
                    termId_Record.get(s)) {
                locations += r.getLocation() + ":";
            }
            locations = locations.substring(0, locations.length() - 1);
            invertedIndex.setLocations(locations);
////            System.out.println(invertedIndex);
            invertedIndexService.insert(invertedIndex);
        }
    }

    @Test
    public void createFullIndex() {
        List<InvertedIndex> invertedIndexList = new ArrayList<>();
        invertedIndexList = invertedIndexService.selectByIndexType("1011");
//        System.out.println(invertedIndexList.size());
        Map<String, List<InvertedIndex>> termInvertedIndex = new HashMap<>();
        String term;
        //按term聚合Inverted Index
        for (InvertedIndex i :
                invertedIndexList) {
            List<InvertedIndex> invertedIndexes = new ArrayList<>();
            term = i.getTerm();
            if (termInvertedIndex.containsKey(term)) {
                invertedIndexes = termInvertedIndex.get(term);
                invertedIndexes.add(i);
                termInvertedIndex.put(term, invertedIndexes);
            } else {
                invertedIndexes.add(i);
                termInvertedIndex.put(term, invertedIndexes);
            }
        }
//        System.out.println(termInvertedIndex.size());
        for (String s :
                termInvertedIndex.keySet()) {
            String ids = "";
            InvertedIndex tempInvertedIndex = termInvertedIndex.get(s).get(0);
            FullIndex fullIndex = new FullIndex();
            fullIndex.setTerm(tempInvertedIndex.getTerm());
            fullIndex.setIndexType(tempInvertedIndex.getIndexType());
            fullIndex.setDf(termInvertedIndex.get(s).size());
            //构造ids
            for (InvertedIndex i :
                    termInvertedIndex.get(s)) {
                ids += i.getDocId() + ":";
            }
            ids = ids.substring(0, ids.length() - 1);
            fullIndex.setIds(ids);
            fullIndexService.insert(fullIndex);
        }

    }


}
