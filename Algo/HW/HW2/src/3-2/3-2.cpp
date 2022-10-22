#include <iostream>

using namespace std;
const int N = 110;
int dp[N][N], num[N][N];
// DP(i,j) is score from i to j.
// DPmin(i,j) = min{DP(i,k) + DP(k+1,j) + num[i][i + j]}
int DPmin(int n)
{
    for (int j = 2; j < n; j++){
        for (int i = 0; i + j < n; i++){
            for (int k = 0; k < j; k++){
                if (k == 0){
                    num[i][i + j] = num[i][i + k] + num[i + k + 1][i + j];
                    dp[i][i + j] = dp[i][i + k] + dp[i + k + 1][i + j] + num[i][i + j];
                }
                else dp[i][i + j] = min(dp[i][i + j], dp[i][i + k] + dp[i + k + 1][i + j] + num[i][i + j]);
            }
        }
    }
    return dp[0][n - 1];
}

// DPmax(i,j) = max{DP(i,k) + DP(k+1,j) + num[i][i + j]}
int DPmax(int n)
{
    for (int j = 2; j < n; j++){
        for (int i = 0; i + j < n; i++){
            for (int k = 0; k < j; k++){
                if (k == 0){
                    num[i][i + j] = num[i][i + k] + num[i + k + 1][i + j];
                    dp[i][i + j] = dp[i][i + k] + dp[i + k + 1][i + j] + num[i][i + j];
                }
                else dp[i][i + j] = max(dp[i][i + j], dp[i][i + k] + dp[i + k + 1][i + j] + num[i][i + j]);
            }
        }
    }
    return dp[0][n - 1];
}

int main()
{
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    int n;
    cin >> n;
    for (int i = 0; i < n; i++)
    {
        cin >> num[i][i];
        if (i > 0) num[i - 1][i] = dp[i - 1][i] = num[i - 1][i - 1] + num[i][i];
    }
    cout << DPmin(n) << endl << DPmax(n) << endl;
    return 0;
}

/*
void printmax(int row, int column)
{
    for(int i = 0;i<row;i++){
        for (int j = 0; j < column; j ++ ){
            cout<<" "<<dp[i][j];
        }
        cout<<endl;
    }
    cout<<endl;
    for(int i = 0;i<row;i++){
        for (int j = 0; j < column; j ++ ){
            cout<<" "<<num[i][j];
        }
        cout<<endl;
    }
    cout<<"++++++++"<<endl;
}
*/