#include <iostream>

using namespace std;
const int N = 110;
int price[N][N];

int main()
{
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    int n; cin >> n;
    for(int i = 0; i < n; i++) for(int j = i+1; j < n; j++) cin >> price[i][j];
    
    for (int j = 2; j < n; j++){
        for (int i = 0; i + j < n; i++){
            for(int k = i + 1; k < j; k ++){
                price[i][j] = min(price[i][k] + price[k][j], price[i][j]);
            }
        }
    }
    cout << price[0][n-1];
    return 0;
}


