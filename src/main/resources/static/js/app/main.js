const main = {
    init: function () {
        let _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
    },
    save: function () {
        let _this = this;
        console.log("hello");
        let data = {
            summonerName: $('#summonerName').val(),
            mainPosition: $('#mainPosition').val(),
            subPositions: $('#subPositions').val(),
            currentTierRank: $('#currentTierRank').val(),
            currentTierLevel: $('#currentTierLevel').val(),
            highestTierRank: $('#highestTierRank').val(),
            highestTierLevel: $('#highestTierLevel').val(),
            comment: $('#comment').val(),
        };
        console.log(data);
        if (_this.isEmpty(data)) {
            _this.warning();
            return;
        }
        let requestData = {
            summonerName: data.summonerName,
            mainPosition: data.mainPosition,
            subPositions: data.subPositions,
            currentTier: data.currentTierRank==="unranked"?"unranked": data.currentTierRank+data.currentTierLevel,
            highestTier: data.highestTierRank==="unranked"?"unranked": data.highestTierRank+data.highestTierLevel,
            comment: data.comment
        }
        $.ajax({
            type: 'POST',
            url: '/participants',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(requestData)
            ,
            beforeSend: function () {
                _this.clear();
                _this.disable();
                $('#gameRecord').val('');
            },
            success: function (res) {
                $('#alertSuccess').show();
                setTimeout($('#alertSuccess').modal('hide'), 5000);
            },
            error: function (res) {
                $('#alertFail').show();
            },
            complete: function (res) {
                _this.enable();
                _this.clearModal();
            }
        });
    },
    isEmpty : function isEmptyDataFilled(data) {
        if (data.summonerName === "" || data.mainPosition === "" ||
            data.currentTierRank === null || data.currentTierLevel === null ||
            data.highestTierLevel === null || data.highestTierRank === null)
            return true;
        else return false;
    },
    disable : function disableRegister() {
        var btn = $('#btnRegister');
        var html = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>';
        btn.attr('disabled', true);
        btn.html(html);
    },
    enable: function enableRegister() {
        var btn = $('#btnRegister');
        btn.attr('disabled', false);
        btn.html('등록');
    },
    clear: function clearAlert() {
        $('div[name=alert]').hide();
    },
    clearModal: function clearModal() {
        console.log($('#saveParticipantsModal'));
        $('#saveParticipantsModal').modal('hide');
    },
    warning: function (res) {
        $('#alertWarning').show();
    },
};
main.init();