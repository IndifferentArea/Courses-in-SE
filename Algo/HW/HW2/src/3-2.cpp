#include<iostream>

using namespace std;
const int N = 110;



// DPmin(i,j) = min{P(i,k) + P(k+1,j)}
int DPmin(int a[], int m){
    int dp[N][N];
    for(int i = 0; i < m; i++) memset(dp+i,1,N * sizeof(int));
    
    for(int i = 0; i < m; i ++){
        i = 0;
    }

}

// DPmax(i,j) = max{P(i,k) + P(k+1,j)}
int DPmax(int a[], int m){
    
}


int main(){
    int n, ori[110]; cin >> n;
    for(int i = 0; i < n; i ++) cin >> ori[i];
    cout << DPmin(ori,n) << endl << DPmax(ori,n) << endl;
    return 0;
}

