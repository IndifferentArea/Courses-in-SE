#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

int main()
{
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    int n,k; cin >> n >> k;
    int tmp_n = n, num = 0;
    for(int i = 0;i < k+1;i++){
        int tmp; cin >> tmp;
        if(tmp > n){
            cout << "No Solution";
            return 0;
        }
        if(tmp_n > tmp) tmp_n -= tmp;
        else tmp_n = n - tmp, num++;
    }
    cout << num;
    return 0;
}