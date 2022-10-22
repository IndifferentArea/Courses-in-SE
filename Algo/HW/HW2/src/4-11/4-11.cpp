#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;


int main()
{
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    string ori; int n; cin >> ori >> n;
    for(int i = 0; i < n; i ++)
        ori.erase(max_element(ori.begin(),ori.end()));
    cout << ori;
    return 0;
}



