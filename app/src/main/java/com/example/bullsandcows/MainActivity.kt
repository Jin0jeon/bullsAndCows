package com.example.bullsandcows

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bullsandcows.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val numbers = arrayOfNulls<Int>(3)  // [null, null, null]


    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private var count : Int = 0
    private lateinit var answer : List<Int>
    private val textViewArray = Array(5) { arrayOfNulls<TextView>(5) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        answer = generateUniqueNumbers()

        binding.btnTry.setOnClickListener {
            check(answer)
        }

        val gridView = binding.gridView

        val items = listOf( // 버튼 클릭시 비활성화 기능 추가할것
            GridItem("1") { test(1) },
            GridItem("2") { test(2) },
            GridItem("3") { test(3) },
            GridItem("4") { test(4) },
            GridItem("5") { test(5) },
            GridItem("6") { test(6) },
            GridItem("7") { test(7) },
            GridItem("8") { test(8) },
            GridItem("9") { test(9) },
            GridItem("0") { test(0) },
            GridItem("⌫") { deleteNum()},
            GridItem("↻"){reset()}
        )
        val adapter = CustomGridAdapter(this, items)
        gridView.adapter = adapter


    }

    fun check(answer: List<Int>) {
        var strike = 0
        var ball = 0
        if(numbers.any{it == null}){
            Toast.makeText(this, "숫자를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        // 스트라이크와 볼 계산
        for (i in 0..2) {
            if (numbers[i] == answer[i]) {
                strike++
            } else if (answer.contains(numbers[i]) && numbers[i] != answer[i]) {
                ball++
            }
        }
        count++

        // 결과 메세지
        var resultMessage = ""
        var recordMessage = ""
        if (strike == 0 && ball == 0) {
            resultMessage = "아웃"
            recordMessage = "아웃"
        } else if (strike != 3) {
            resultMessage = "${strike}스트라이크 ${ball}볼"
            recordMessage = "${strike}S ${ball}B"
        } else {
            resultMessage = "홈런! $count 번 만에 성공"
            recordMessage = "홈런"
            binding.tvCheck.background = AppCompatResources.getDrawable(this, R.drawable.win)
        }

        // 결과 표시
        binding.tvCheck.text = resultMessage


        // 기록 업데이트
        val currentText = binding.tvRecord.text.toString()
        val newText = currentText + numbers.joinToString("") + " : " + recordMessage + "\n "
        binding.tvRecord.text = newText

        // numbers 배열 초기화
        numbers[0] = null
        numbers[1] = null
        numbers[2] = null

        // UI 업데이트
        binding.tv1st.text = ""
        binding.tv2nd.text = ""
        binding.tv3rd.text = ""
    }


    private fun generateUniqueNumbers(): List<Int> {
        // 1부터 9까지의 숫자 리스트 생성
        val availableNumbers = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

        // 중복 없는 3개의 숫자를 뽑기
        val randomNumbers = mutableListOf<Int>()

        for (i in 0 until 3) {
            // 리스트에서 랜덤한 숫자 하나 뽑기
            val randomIndex = Random.nextInt(availableNumbers.size)
            val number = availableNumbers[randomIndex]

            // 뽑은 숫자 리스트에서 제거
            availableNumbers.removeAt(randomIndex)

            // 뽑은 숫자를 결과 리스트에 추가
            randomNumbers.add(number)
        }

        return randomNumbers
    }

    private fun test(i : Int) {
        if( numbers[0] == null ){
            numbers[0] = i
            binding.tv1st.text = numbers[0].toString()
        }
        else if( numbers[1] == null ){
            numbers[1] = i
            binding.tv2nd.text = numbers[1].toString()
        }else if( numbers[2] == null) {
            numbers[2] = i
            binding.tv3rd.text = numbers[2].toString()
        }else{
            Toast.makeText(this, "꽉 찼음", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteNum() {
        if (numbers[2] != null) {
            numbers[2] = null
            binding.tv3rd.text = ""
        }else if(numbers[1] != null){
            numbers[1] = null
            binding.tv2nd.text = ""
        }else if(numbers[0] != null){
            numbers[0] = null
            binding.tv1st.text = ""
        }
    }

    private fun reset(){
        numbers[0] = null
        numbers[1] = null
        numbers[2] = null
        binding.tv1st.text = ""
        binding.tv2nd.text = ""
        binding.tv3rd.text = ""
        binding.tvCheck.text = ""
        binding.tvRecord.text = ""
        answer = generateUniqueNumbers()
        binding.tvCheck.background = null
        count = 0;
    }
}
