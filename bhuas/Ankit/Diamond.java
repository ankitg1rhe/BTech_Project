import java.util.*;
import java.io.*;
import java.lang.*;

public class Diamond{
	static int R,C;

	static int solveMine(List<List<Integer>> li){
		R = li.size();
		C = li.get(0).size();
		int[][] mat = new int[R][C];
		String[][] ms = new String[R][C];
		for(int i=0; i<li.size(); i++)
			for(int j=0; j<li.get(0).size(); j++){
				mat[i][j] = li.get(i).get(j);
				ms[i][j] = "";
			}
		
		boolean noab = true;
		int sum = mat[0][0], x;
		for( x=1; x<C; x++){
			if(mat[0][x] == -1) break;
			sum += mat[0][x];
			mat[0][x] = sum;
			ms[0][x] = ms[0][x-1] + "0";
		}
		
		sum = mat[0][0];
		for( x=1; x<R; x++){
			if(mat[x][0] == -1) break;
			sum += mat[x][0];
			mat[x][0] = sum;
			ms[x][0] = ms[x-1][0] + "1";
		}
		
		for(int i=1; i<R; i++){
			for(int j=1; j<C; j++){
				if(mat[i][j] == -1) continue;
				
				if(mat[i][j-1] > mat[i-1][j]){
					mat[i][j] += mat[i][j-1];
					ms[i][j] = ms[i][j-1] + "0";
				}else{
					mat[i][j] += mat[i-1][j];
					ms[i][j] = ms[i-1][j] + "1";
				}
			}
		}
		
		int ans = mat[R-1][C-1];
		String sans = ms[R-1][C-1];
		
		for(int i=0; i<li.size(); i++)
			for(int j=0; j<li.get(0).size(); j++)
				mat[i][j] = li.get(i).get(j);
		
		int r=0, c=0;
		mat[r][c] = 0;
		for(int i=0; i<sans.length(); i++){
			mat[r][c] = 0;
			if(sans.charAt(i) == '0') ++c;
			else ++r;
		}
		
		sum = 0;
		for( x=1; x<C; x++){
			if(mat[0][x] == -1) break;
			sum += mat[0][x];
			mat[0][x] = sum;
			ms[0][x] = ms[0][x-1] + "0";
		}
		
		sum = 0;
		for( x=R-2; x>=0; x--){
			if(mat[x][C-1] == -1) break;
			sum += mat[x][C-1];
			mat[x][C-1] = sum;
		}
		
		for(int i=R-2; i>=0; i--){
			for(int j=C-2; j>=0; j--){
				if(mat[i][j] == -1) continue;
				
				if(mat[i][j+1] >= mat[i+1][j]){
					mat[i][j] += mat[i][j+1];
				}else{
					mat[i][j] += mat[i+1][j];
				}
			}
		}
		
		ans += mat[0][0];
		
		return ans;
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String l1 = br.readLine();
		int r = Integer.parseInt(l1.split("\\s+")[0]);
		int c = Integer.parseInt(l1.split("\\s+")[1]);

		List<List<Integer>> li = new ArrayList<>();
		for(int i=0; i<r; ++i){
			List<Integer> xl = new ArrayList<>();
			String line = br.readLine();
			String[] sp = line.split("\\s+");
			li.add(xl);

			for(int j=0; j<sp.length; j++) li.get(i).add(Integer.parseInt(sp[j]));
		}

		int ans = solveMine(li);
		System.out.println(ans);
	}
}
 