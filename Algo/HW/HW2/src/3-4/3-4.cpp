#include <iostream>

using namespace std;

const int N = 110;
// dp[i][j] represents max path from top to i th floor and j th number.
// dp[i][j] = max{dp[i-1][j],dp[i-1][j-1]}+num[i,j];
int dp[N][N];

int main()
{
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    int n,m; cin >> n;
    cin >> dp[0][0];
    for(int i = 1; i < n; i ++){
        cin >> m; dp[i][0] = dp[i-1][0] + m;
        for(int j = 1; j <= i; j++){
            cin >> m;
            dp[i][j] = max(dp[i-1][j], dp[i-1][j-1]) + m;
        }
    }
    int res = 0;
    for(int i = 0; i < n; i ++) res = max(res, dp[n-1][i]);
    cout << res;
    return 0;
}