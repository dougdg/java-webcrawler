package com.dougdg.webcrawler.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dougdg.webcrawler.dao.LinkDao;

/**
 * @author Douglas Diez Goncalves (dougdiez@gmail.com)
 * 
 *         WebCrawler program to fetch URLs from a given URL. There is a depth
 *         to limit recursive loop when fetching URLs and avoid some problems.
 *         Please, for now, set manually your desired URL and depth.
 * 
 */

public class Controller {

	// User input variables:
	// MAX_DEPTH = Limit the max depth of the loop
	// URL = URL to start the crawling
	// depth = looping counter
	private static String URL = "";
	private static int maxDepth = 0;
	private static int depth = 0;
	// -------------------------------------------------------------
	private static TreeSet<String> linksSet;

	public Controller() {
	}

	public static void main(String[] args) {
		
		System.out.println("Starting WebCrawler...");

		getUserInput();

		linksSet = new TreeSet<>();

		fetchURL(URL, depth);

		manageLinks();

		System.out.println("...End of program.");
	}

	public static void getUserInput() {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));			

			System.out.println("User input variables :");
			System.out.println("URL    (string) --> URL to start the crawling (like http://www.ibm.com)");
			System.out.println("MAX_DEPTH (int) --> Limit the max diving of the iteration thru URLs");

			while (URL.isEmpty() || URL.equals("") || !URL.startsWith("http://")) {
				System.out.println("\n Please enter below the URL: ");
				URL = br.readLine();
			}

			while (!(maxDepth == (int)maxDepth) || maxDepth == 0) {
				System.out.println("Please enter below a integer greater than 0: \n");
				maxDepth = Integer.parseInt(br.readLine());
			}
			
		} catch (IOException e) {
			System.err.println("Exception ocurred during user input: " + e.getMessage());
		}

	}

	public static void fetchURL(String URL, int depth) {

		// Check if the Set already contain this URL, if not and inside depth,
		// the URL will be added, so this keep looping recursively to check
		// if there are URLs to be crawled inside the depth limit

		if (!linksSet.contains(URL) && (depth < maxDepth)) {
			System.out.println("Depth: " + depth + " URL: " + URL);

			try {
				linksSet.add(URL);

				Document doc = Jsoup.connect(URL).get();
				Elements fetchedURL = doc.select("a[href]");

				depth++;
				for (Element page : fetchedURL) {
					fetchURL(page.attr("abs:href"), depth);
				}

			} catch (Exception e) {
				System.err.println("\n"+e.getMessage()+": [ " + URL + " ]\n");
				return;
			}
		}
	}

	public static void manageLinks() {

		ArrayList<String> linksList = new ArrayList<String>();
		LinkDao LinkDAO = new LinkDao();
		LinkDAO.createLinksTable();
		LinkDAO.insertLinks(linksSet);
		linksList = LinkDAO.selectLinks();

		for (String link : linksList) {
			System.out.println("Link from sqlite : " + link);
		}

	}

}
