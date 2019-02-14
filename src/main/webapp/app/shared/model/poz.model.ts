import { IBirim } from 'app/shared/model/birim.model';
import { IMalzeme } from 'app/shared/model/malzeme.model';
import { IIscilik } from 'app/shared/model/iscilik.model';

export interface IPoz {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    yil?: number;
    fiyatTelekom?: number;
    fiyatGirisim?: number;
    fiyatTaseron?: number;
    fiyatTasere?: number;
    kdvOran?: number;
    malzemeMi?: boolean;
    birim?: IBirim;
    malzeme?: IMalzeme;
    iscilik?: IIscilik;
}

export class Poz implements IPoz {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public kisaltma?: string,
        public yil?: number,
        public fiyatTelekom?: number,
        public fiyatGirisim?: number,
        public fiyatTaseron?: number,
        public fiyatTasere?: number,
        public kdvOran?: number,
        public malzemeMi?: boolean,
        public birim?: IBirim,
        public malzeme?: IMalzeme,
        public iscilik?: IIscilik
    ) {
        this.malzemeMi = this.malzemeMi || false;
    }
}
