package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Stack;

public class main {
	public static void main(String[] args) {
		String data = extractData();
		ArrayList<ArrayList<String>> laby = fairematrice(data);
		Graph labygraphe = construireGraphe(laby);
		// int countnodes = labygraphe.getAdjVertices().size();
		System.out.print(parcours_largeur(labygraphe));
	}

	static int parcours_largeur(Graph labygraphe) {
		Stack<Vertex> file_vertexes = new Stack<Vertex>();
		ArrayList<Vertex> marqued = new ArrayList<Vertex>();
		Map<Vertex, Integer> mapdist = new HashMap<Vertex, Integer>();

		for (Vertex vertex : labygraphe.getAdjVertices().keySet()) {
			if (vertex.label.contains("S")) {
				file_vertexes.add(vertex);
				marqued.add(vertex);
				mapdist.put(vertex, 0);
			} else {
				mapdist.put(vertex, Integer.MAX_VALUE);
			}
		}
		while (!file_vertexes.empty()) {
			Vertex current = file_vertexes.pop();
			System.out.println(current.label);
			if (current.label.contains("E")) {
				// sortie
				return mapdist.get(current);
				
			}
			for (Vertex v : labygraphe.getAdjVertices().get(current)) {

				if (!marqued.contains(v)) {
					file_vertexes.add(v);
					marqued.add(v);
					mapdist.replace(v, mapdist.get(current) + 1);
					// trouver un marquage et une sortie
				}

			}
		}
		// System.out.println("A");
		return -1;
	}

//a garder ?
	private static int parcourirlab(ArrayList<ArrayList<String>> laby) {
		// System.out.println(laby.get(0).size());
		int y = -1;
		int found = -1;
		while (found == -1) {
			y++;
			found = laby.get(y).indexOf("S");
		}
		int x = found;
		return parcourir(x, y, laby);

	}

	private static int parcourir(int x, int y, ArrayList<ArrayList<String>> laby) {
		LinkedList<int[]> pathway = new LinkedList<int[]>();
		ArrayList<int[]> visitedNodes = new ArrayList<int[]>();
		int[] start = { x, y, 0 };
		pathway.add(start);
		while (!pathway.isEmpty()) {

			int[] testednode = pathway.pop();
			int xtest = testednode[0];
			int ytest = testednode[1];
			char currentsquare = laby.get(xtest).get(ytest).charAt(0);
			// System.out.println(xtest+" "+ytest);
			int[] square = { xtest, ytest };
			visitedNodes.add(square);
			if (currentsquare == 'S') {
				currentsquare = 'a';
			}
			if (currentsquare == 'E') {
				System.out.println("weee");
				return testednode[2];

			}
			int[][] pos_adjcells = { { xtest - 1, ytest }, { xtest + 1, ytest }, { xtest, ytest - 1 },
					{ xtest, ytest + 1 } };
			// System.out.println("-");
			for (int[] pos_adjcell : pos_adjcells) {
				if (!isInList(visitedNodes, pos_adjcell)) {
					if (pos_adjcell[0] >= 0 && pos_adjcell[1] >= 0 && pos_adjcell[0] < laby.size()
							&& pos_adjcell[1] < laby.get(0).size()) {
						char adjcell = laby.get(pos_adjcell[0]).get(pos_adjcell[1]).charAt(0);
						// System.out.println("case"+pos_adjcell[0]+'-'+pos_adjcell[1]+':'+adjcell);
						if (adjcell == 'E') {
							adjcell = 'z';
						}
						if (Math.abs(currentsquare - adjcell) < 2) {
							int[] newnode = { pos_adjcell[0], pos_adjcell[1], testednode[2] + 1 };
							pathway.add(newnode);
						}
					}
				}
			}
			// System.out.print("a");
		}
		return -1;
	}

	private static ArrayList<ArrayList<String>> fairematrice(String data) {
		ArrayList<ArrayList<String>> laby = new ArrayList<ArrayList<String>>();
		String[] lines = { "" };
		if (data.contains(System.lineSeparator())) {
			lines = data.split(System.lineSeparator());
		} else {
			lines = data.split("\n");
		}

		for (String line : lines) {
			ArrayList<String> ligne = new ArrayList<String>();
			for (int i = 0; i < line.length(); i++) {
				ligne.add(line.substring(i, i + 1));
			}
			laby.add(ligne);
		}
		return laby;
	}

	private static Graph construireGraphe(ArrayList<ArrayList<String>> laby) {
		Graph labygraphe = new Graph();
		for (int i = 0; i < laby.size(); i++) {
			for (int j = 0; j < laby.get(0).size(); j++) {
				labygraphe.addVertex(i + "-" + j + ":" + laby.get(i).get(j));
			}
		}
		System.currentTimeMillis();
		for (int i = 0; i < laby.size(); i++) {
			for (int j = 0; j < laby.get(0).size(); j++) {
				char ogChar = laby.get(i).get(j).charAt(0);
				if (ogChar == 'S') {
					ogChar = 'a';
				}
				if (ogChar == 'E') {
					ogChar = 'z';
				}
				if (i < laby.size() - 1) {
					char rightChar = laby.get(i + 1).get(j).charAt(0);
					if (rightChar == 'S') {
						rightChar = 'a';
					}
					if (rightChar == 'E') {
						rightChar = 'z';
					}
					if (Math.abs(ogChar - rightChar) < 2) {
						String str = (i + 1) + "-" + j + ":" + laby.get(i + 1).get(j);
						labygraphe.addEdge(str, i + "-" + j + ":" + laby.get(i).get(j));
					}
				}
				if (j < laby.get(0).size() - 1) {
					char bottomChar = laby.get(i).get(j + 1).charAt(0);
					if (bottomChar == 'S') {
						bottomChar = 'a';
					}
					if (bottomChar == 'E') {
						bottomChar = 'z';
					}
					if (Math.abs(ogChar - bottomChar) < 2) {
						String str = i + "-" + (j + 1) + ":" + laby.get(i).get(j + 1);
						labygraphe.addEdge(str, i + "-" + j + ":" + laby.get(i).get(j));
					}

				}
			}
			// System.out.print("a");
		}

		return labygraphe;
	}

	private static String extractData() {
		String data = null;
		Path myObj = Path.of("C:\\Users\\UNiK\\Downloads\\input");
		try {
			data = Files.readString(myObj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public static boolean isInList(final List<int[]> list, final int[] candidate) {
		for (final int[] item : list) {
			if (Arrays.equals(item, candidate)) {
				return true;
			}
		}
		return false;
	}

}
